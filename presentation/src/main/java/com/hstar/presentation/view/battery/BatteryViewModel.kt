package com.hstar.presentation.view.battery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hstar.base.presentation.BaseViewModel
import com.hstar.base.presentation.util.MutableEventFlow
import com.hstar.base.presentation.util.asEventFlow
import com.hstar.base.presentation.util.ioToMain
import com.hstar.base.util.batteryStatePublishSubject
import com.hstar.domain.battery.entity.BatteryEntity
import com.hstar.domain.battery.usecase.GetBatteryRateUseCase
import com.hstar.domain.battery.usecase.GetBatteryTemperatureUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor(
    private val getBatteryRateUseCase: GetBatteryRateUseCase,
    private val getBatteryTemperatureUseCase: GetBatteryTemperatureUseCase,
) : BaseViewModel() {

//    private val _batteryInfo = MutableLiveData<BatteryEntity>()
//    val batteryInfo: LiveData<BatteryEntity> get() = _batteryInfo

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    init {
//        batteryStatePublishSubject.toFlowable(BackpressureStrategy.BUFFER).publish().replay(1).refCount()
//            .onErrorComplete().subscribe().addToDisposable()
        batteryChanges()
        //getBatteryInfo()
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun batteryChanges() {
        batteryStatePublishSubject
            .ioToMain()
            .doOnError { Logger.e("Error : ${it.message}")}
            .onErrorComplete()
            .subscribe({
                Logger.i("Battery Changed!! : $it")
                event(Event.ChangeCurrentRate(BatteryEntity(it.first, it.second)))
            }, Throwable::printStackTrace)
            .addToDisposable()
    }

//    private fun getBatteryInfo() {
//        getBatteryRateUseCase()
//            .ioToMain()
//            .doOnError { Logger.e("Error : $it") }
//            .onErrorComplete()
//            .subscribeBy {
//                _batteryInfo.value = it
//            }
//            .addToDisposable()
//    }

    sealed class Event() {
        data class ChangeCurrentRate(val currentRate: BatteryEntity) : Event()
        data class ChangeTemperature(val currentTemperature: BatteryEntity) : Event()
    }
}