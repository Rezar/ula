package com.ula.gameapp.core.helper

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

object Paths {
    private var downloadDirectory: File? = null

    fun getAudioCachePath(context: Context?): String? {
        try {
            val path = ContextCompat.getExternalFilesDirs(context!!, null)[0].toString() +
                    File.separator + ".xcache"

            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getDownloadDirectory(context: Context?): File? {
        if (downloadDirectory == null) {
            downloadDirectory = context?.getExternalFilesDir(null)
            if (downloadDirectory == null) {
                downloadDirectory = context?.getFilesDir()
            }
        }
        return downloadDirectory
    }

    fun getMoviesDir(): String {
        return "//android_asset" + File.separator + "Movies" + File.separator;
    }

    fun getExternalStoragePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + File.separator
    }
}