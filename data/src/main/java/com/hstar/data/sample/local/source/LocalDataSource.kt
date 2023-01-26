package com.hstar.data.sample.local.source

import com.hstar.data.sample.local.model.BatteryInfo
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {
    // 1. 배터리 잔량
    fun getBatteryRate(rate: Float, temperature: Float): Single<BatteryInfo>

    // 2. 배터리 온도
    fun getBatterTemperature(): Single<BatteryInfo>
}