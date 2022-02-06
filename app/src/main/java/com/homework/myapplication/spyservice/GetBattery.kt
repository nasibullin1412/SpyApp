package com.homework.myapplication.spyservice

import android.content.Context
import android.os.BatteryManager

class GetBattery : (Context) -> (Int) {
    override fun invoke(context: Context): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }
}
