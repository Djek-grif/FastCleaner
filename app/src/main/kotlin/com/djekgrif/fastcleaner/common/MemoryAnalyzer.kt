package com.djekgrif.fastcleaner.common

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.djekgrif.fastcleaner.App
import com.djekgrif.fastcleaner.utils.Logger
import java.io.BufferedReader
import java.io.FileReader

/**
 * Created by djek-grif on 10/26/17.
 */
class MemoryAnalyzer(private val app: App) {

    fun getAvailableMemory(): Long {
        val activityManager = app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem
    }

    fun getTotalMemory(): Long {
        val file = "/proc/meminfo"
        val memInfo: String
        val strs: Array<String>
        var memory: Long = 0

        try {
            val bufferedReader = BufferedReader(FileReader(file), 8192)
            memInfo = bufferedReader.readLine()
            strs = memInfo.split("\\s+".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (str in strs) {
                Logger.d(str + "\t")
            }
            // in KB
            memory = Integer.valueOf(strs[1])!!.toInt().toLong()
            bufferedReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return memory * 1024
    }

    fun isSDCardInfoAvailable(): Boolean = Environment.isExternalStorageRemovable()
            && Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getSDCardInfo(): Pair<Long, Long> {
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = statFs.blockSizeLong
        val totalBlocks = statFs.blockCountLong
        val availableBlocks = statFs.availableBlocksLong

        val total = totalBlocks * blockSize
        val available = availableBlocks * blockSize
        return Pair(available, total)
    }

    fun getSystemSpaceInfo(): Pair<Long, Long> {
        val statFs = StatFs(Environment.getDataDirectory().path)
        val blockSize = statFs.blockSizeLong
        val totalBlocks = statFs.blockCountLong
        val availableBlocks = statFs.availableBlocksLong

        val total = totalBlocks * blockSize
        val available = availableBlocks * blockSize
        return Pair(available, total)

    }
}