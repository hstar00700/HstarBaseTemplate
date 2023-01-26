package com.hstar.data.sample.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BatteryInfo(
    val currentBatteryRate: Float = 0.0f,
    val temperature: Float = 0.0f
) : Parcelable