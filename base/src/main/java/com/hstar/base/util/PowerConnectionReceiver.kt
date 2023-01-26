package com.hstar.base.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.orhanobut.logger.Logger

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        //val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryTemperature = intent!!.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10
        Logger.w("data changes!! - level($level) // temperature : $batteryTemperature")

        runCatching {
            level!! / scale?.toFloat()!!
        }
            .onFailure { it.printStackTrace() }
            .getOrDefault(0.0f)
            .also {
                Logger.w("batteryStatePublishSubject : onNext!! - $it // temp : $batteryTemperature")
                batteryStatePublishSubject.onNext(it * 100 to batteryTemperature)
            }

    }
}
