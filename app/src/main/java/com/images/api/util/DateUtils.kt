package com.images.api.util

import java.util.*


/**
 * Created by ZEESHAN on 5/12/2023.
 */
object DateUtils {

    fun excludeHours(hours: Int = -12): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours)
        return calendar.timeInMillis
    }

}