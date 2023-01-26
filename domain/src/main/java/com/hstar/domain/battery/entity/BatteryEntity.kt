package com.hstar.domain.battery.entity

import com.hstar.base.domain.model.Entity

data class BatteryEntity (
    val currentRate: Float = 0.0f,
    val temperature: Float = 0.0f
): Entity