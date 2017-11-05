package com.djekgrif.fastcleaner.utils

import android.util.Log
import java.util.regex.Pattern

/**
 * Created by djek-grif on 8/6/17.
 */
object Logger {

    val LIFECYCLE: String = "LIFECYCLE"

    private val D = 1
    private val I = 2
    private val W = 3
    private val E = 4

    private val LOG_SEPARATOR = "\t"
    private val CALL_STACK_INDEX = 3
    private val MAX_LOG_LENGTH = 4000
    private val MAX_TAG_LENGTH = 23

    enum class Level {
        DEFAULT, DETAILS
    }


    private val explicitTag = ThreadLocal<String>()
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    var currentLevel = Level.DEFAULT

    fun d(message: String) {
        Logger.d(message, null, Level.DEFAULT)
    }

    fun d(message: String, module: String) {
        Logger.d(message, module, Level.DEFAULT)
    }

    fun d(message: String, level: Level) {
        Logger.d(message, null, level)
    }

    fun d(message: String, module: String?, level: Level) {
        val tag = getTag()
        addLog(tag, message, module, level, D)
    }


    fun i(message: String) {
        Logger.i(message, null, Level.DEFAULT)
    }

    fun i(message: String, module: String) {
        Logger.i(message, module, Level.DEFAULT)
    }

    fun i(message: String, level: Level) {
        Logger.i(message, null, level)
    }

    fun i(message: String, module: String?, level: Level) {
        val tag = getTag()
        addLog(tag, message, module, level, I)
    }

    fun w(message: String) {
        Logger.w(message, null, Level.DEFAULT)
    }

    fun w(message: String, module: String) {
        Logger.w(message, module, Level.DEFAULT)
    }

    fun w(message: String, level: Level) {
        Logger.w(message, null, level)
    }

    fun w(message: String, module: String?, level: Level) {
        val tag = getTag()
        addLog(tag, message, module, level, W)
    }


    fun e(message: String) {
        Logger.e(null, message, null, Level.DEFAULT)
    }

    fun e(t: Throwable, message: String) {
        Logger.e(t, message, null, Level.DEFAULT)
    }

    fun e(message: String, module: String) {
        Logger.e(null, message, module, Level.DEFAULT)
    }

    fun e(t: Throwable, message: String, module: String) {
        Logger.e(t, message, module, Level.DEFAULT)
    }

    fun e(message: String, level: Level) {
        Logger.e(null, message, null, level)
    }

    fun e(t: Throwable, message: String, level: Level) {
        Logger.e(t, message, null, level)
    }

    fun e(t: Throwable?, message: String, module: String?, level: Level) {
        val tag = getTag()
        addLog(t, tag, message, module, level, E)
    }

    private fun addLog(tag: String, message: String, module: String?, level: Level, priority: Int) {
        addLog(null, tag, message, module, level, priority)
    }

    private fun addLog(t: Throwable?, tag: String, message: String, module: String?, level: Level, priority: Int) {
        if (currentLevel == level || level == Level.DEFAULT) {
            val logMessage = if (message.length > MAX_LOG_LENGTH) message.substring(0, MAX_LOG_LENGTH) else message
            val log = buildLog(module, logMessage)
            when (priority) {
                D -> Log.d(tag, log)
                I -> Log.i(tag, log)
                W -> Log.w(tag, log)
                E -> if (t != null) {
                    Log.e(tag, log, t)
                } else {
                    Log.e(tag, log)
                }
            }
        }
    }

    private fun getTag(): String {
        explicitTag.get()?.let {
            explicitTag.remove()
            return it
        }
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            Log.e("Logger", "Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        var tag = stackTrace[CALL_STACK_INDEX].className
        val m = ANONYMOUS_CLASS.matcher(tag!!)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        return if (tag.length > MAX_TAG_LENGTH) tag.substring(0, MAX_TAG_LENGTH) else tag
    }

    private fun buildLog(module: String?, message: String): String {
        return Thread.currentThread().name +
                (if (module != null) LOG_SEPARATOR + module else "") +
                LOG_SEPARATOR + message
    }
}