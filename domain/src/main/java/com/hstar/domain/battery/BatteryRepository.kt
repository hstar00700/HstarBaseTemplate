package com.hstar.domain.battery

import com.hstar.domain.battery.entity.BatteryEntity
import io.reactivex.rxjava3.core.Single

interface BatteryRepository {
    fun getBatteryRate(rate: Float, temp: Float): Single<BatteryEntity>
    fun getBatteryTemperature(): Single<BatteryEntity>
}