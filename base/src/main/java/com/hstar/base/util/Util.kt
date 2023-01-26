package com.hstar.base.util

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

fun Context.registerBatteryReceiver(receiver: PowerConnectionReceiver): Intent? {
    val intentFilter = IntentFilter().apply {
        addAction(Intent.ACTION_POWER_CONNECTED)
        addAction(Intent.ACTION_POWER_DISCONNECTED)
        addAction(Intent.ACTION_BATTERY_CHANGED)
    }

    runCatching { this.registerReceiver(receiver, intentFilter) }
        .getOrNull()
        .also { return it }
}
