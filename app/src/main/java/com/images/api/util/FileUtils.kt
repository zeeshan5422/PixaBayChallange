package com.images.api.util

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

object FileUtils {
    fun loadJSONStringFromAsset(context: Context, path: String): String? {
        val json: String = try {
            val mFile = context.assets.open("$path.json")
            val size = mFile.available()
            val buffer = ByteArray(size)
            mFile.read(buffer)
            mFile.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}