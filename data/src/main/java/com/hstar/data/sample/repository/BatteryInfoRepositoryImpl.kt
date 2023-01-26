package com.hstar.data.sample.repository

import com.hstar.data.sample.local.source.LocalDataSource
import com.hstar.domain.battery.BatteryRepository
import com.hstar.domain.battery.entity.BatteryEntity
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BatteryInfoRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : BatteryRepository {

    override fun getBatteryRate(rate: Float, temp: Float): Single<BatteryEntity> {
        return localDataSource.getBatteryRate(rate, temp)
            .map {
                BatteryEntity(
                    currentRate = rate,
                    temperature = temp
                )
            }
    }

    override fun getBatteryTemperature(): Single<BatteryEntity> {
        return localDataSource.getBatterTemperature()
            .map {
                BatteryEntity(
                    currentRate = it.currentBatteryRate,
                    temperature = it.temperature
                )
            }
    }
}