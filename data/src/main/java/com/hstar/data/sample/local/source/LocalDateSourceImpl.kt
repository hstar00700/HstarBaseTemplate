package com.hstar.data.sample.local.source

import com.hstar.base.util.batteryStatePublishSubject
import com.hstar.data.sample.local.model.BatteryInfo
import io.reactivex.rxjava3.core.Single

class LocalDateSourceImpl : LocalDataSource {

    override fun getBatteryRate(rate: Float, temperature: Float): Single<BatteryInfo> {
        return Single.just(BatteryInfo(
                currentBatteryRate = rate,
                temperature = temperature
            ))

    }

    override fun getBatterTemperature(): Single<BatteryInfo> {
        return batteryStatePublishSubject.map {
            BatteryInfo(
                currentBatteryRate = it.first,
                temperature = 0.0f
            )
        }.single(BatteryInfo())
    }
}