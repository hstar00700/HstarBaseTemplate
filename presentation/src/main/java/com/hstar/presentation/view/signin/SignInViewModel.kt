package com.hstar.presentation.view.signin

import androidx.lifecycle.viewModelScope
import com.hstar.base.presentation.BaseViewModel
import com.hstar.base.presentation.util.ioToMain
import com.hstar.domain.sample.entity.SignInEntity
import com.hstar.domain.sample.entity.UserEntity
import com.hstar.domain.sample.usecase.LoginUseCase
import com.hstar.presentation.comon.globalChe
import com.hstar.util.MutableEventFlow
import com.hstar.util.asEventFlow
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: LoginUseCase
) : BaseViewModel() {

    // 이벤트 종속성을 줄이기 위해 Flow를 사용 + 화면 나가기등 데이터 종속성 보존을 위해
    private val _signInEvent = MutableEventFlow<Event>()
    val signInEvent get() = _signInEvent.asEventFlow()

    private fun signIn(id: String, pw: String) {
        viewModelScope.launch(globalChe) {
            withContext(Dispatchers.IO) {
                signInUseCase(id, pw)
                    .ioToMain()
                    .doOnError {
                        //TODO: error Msg!!
                    }
                    .onErrorComplete()
                    .subscribeBy {
                        //TODO: Succecc Check!!
                        sendEvent(Event.LoginEvent(it))
                        Logger.w("SignIn result : $it")
                    }
                    .addToDisposable()
            }
        }
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _signInEvent.emit(event)
        }
    }

    sealed class Event() {
        data class LoadUsersEvent(val listUsers: List<UserEntity>) : Event()
        data class LoginEvent(val userInfo: SignInEntity) : Event()
    }

}