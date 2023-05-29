package com.images.api.util

import com.images.api.util.DateUtils.excludeHours
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*

import org.junit.Test
import java.util.*
import kotlin.math.abs

/**
 * Created by ZEESHAN on 5/15/2023.
 */

class DateUtilsTest {


    @Test
    fun `excludeHours() with default value should return current time minus 12 hours`() {
        val currentTime = Calendar.getInstance().timeInMillis
        val twelveHoursAgo = currentTime - (12 * 60 * 60 * 1000)

        val result = excludeHours()

        assertThat(
            result,
            allOf(greaterThanOrEqualTo(twelveHoursAgo), lessThanOrEqualTo(currentTime))
        )
    }

    @Test
    fun `excludeHours() with positive hours should return current time plus specified hours`() {
        val hoursToExclude = 5
        val currentTime = Calendar.getInstance().timeInMillis
        val expectedTime = currentTime + (hoursToExclude * 60 * 60 * 1000)

        val result = excludeHours(hoursToExclude)

        assertThat(result.toDouble(), closeTo(expectedTime.toDouble(), 500.0))
    }

    @Test
    fun `excludeHours() with negative hours should return current time minus specified hours`() {
        val hoursToInclude = -10
        val currentTime = Calendar.getInstance().timeInMillis
        val expectedTime = currentTime - (abs(hoursToInclude) * 60 * 60 * 1000)

        val result = excludeHours(hoursToInclude)

        assertThat(result.toDouble(), closeTo(expectedTime.toDouble(), 500.0))
    }
}