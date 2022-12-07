package moe.lz233.allwinnera50

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.StatusBarManager
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.os.Build
import moe.lz233.allwinnera50.util.LogUtil
import moe.lz233.allwinnera50.util.SerialPortUtil

class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var sp: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        const val TAG = "AllWinnerA50"
        lateinit var audioManager: AudioManager
        lateinit var statusBarManager: StatusBarManager
        lateinit var notificationManager: NotificationManager
    }

    @SuppressLint("WrongConstant", "NewApi")
    override fun onCreate() {
        super.onCreate()
        LogUtil._d("App started")
        context = applicationContext
        sp = context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        editor = sp.edit()
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