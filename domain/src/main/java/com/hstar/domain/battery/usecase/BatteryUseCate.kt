package com.hstar.domain.battery.usecase

import com.hstar.domain.battery.BatteryRepository
import javax.inject.Inject

class GetBatteryRateUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository
) {
    operator fun invoke(rate: Float, temp: Float) = batteryRepository.getBatteryRate(rate, temp)
}

class GetBatteryTemperatureUseCase @Inject constructor(
    private val batteryRepository: BatteryRepository,
) {
    operator fun invoke() = batteryRepository.getBatteryTemperature()
}