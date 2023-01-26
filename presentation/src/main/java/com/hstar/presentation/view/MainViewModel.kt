package com.hstar.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hstar.base.presentation.BaseViewModel
import com.hstar.base.presentation.util.ioToMain
import com.hstar.domain.sample.entity.UserEntity
import com.hstar.domain.sample.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserDataUseCase
) : BaseViewModel() {

    private val _userList = MutableLiveData<List<UserEntity>>()
    val userList: LiveData<List<UserEntity>> get() = _userList

    init {
        getUser()
    }

    //TODO: change corutine
    private fun getUser() {
        getUserUseCase()
            .ioToMain()
            .onErrorComplete()
            .subscribeBy {
                if (it.isEmpty()) return@subscribeBy
                _userList.value = it
            }
            .addToDisposable()
    }
}