package moe.lz233.allwinnera50

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.StatusBarManager
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import androidx.core.content.getSystemService
import moe.lz233.allwinnera50.util.LogUtil
import moe.lz233.allwinnera50.util.SerialPortUtil

class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var sp: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        const val TAG = "AllWinnerA50"
        lateinit var activityManager: ActivityManager
        lateinit var audioManager: AudioManager
        lateinit var statusBarManager: StatusBarManager
        lateinit var notificationManager: NotificationManager

        fun isServiceExisted(clazz: Class<*>): Boolean {
            activityManager.getRunningServices(Int.MAX_VALUE).forEach {
                LogUtil._d(it.service.className)
                if (it.service.className == clazz.name) {
                    return true
                }
            }
            return false
        }
    }

    @SuppressLint("WrongConstant", "NewApi")
    override fun onCreate() {
        super.onCreate()
        LogUtil._d("App started")
        context = applicationContext
        sp = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        editor = sp.edit()
        activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        statusBarManager = getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        SerialPortUtil.openSerialPort()
        App.notificationManager.createNotificationChannels(
            listOf(
                NotificationChannel("233", "KeyBoardService", NotificationManager.IMPORTANCE_LOW),
            )
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        SerialPortUtil.closeSerialPort()
    }
}