package com.images.api

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by ZEESHAN on 5/12/2023.
 */

// secure this api key, e.g: store in firebase remote configs.
const val API_KEY = "36304513-2fa549fcc5b6743c72868acca"

@HiltAndroidApp
class AppDelegate : Application() {
}