package moe.lz233.allwinnera50.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.topjohnwu.superuser.Shell
import moe.lz233.allwinnera50.ui.main.MainActivity
import moe.lz233.allwinnera50.util.InputManagerUtil
import moe.lz233.allwinnera50.util.SerialPortUtil

class KeyBoardService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        SerialPortUtil.keyBoardListenerList.add {
            InputManagerUtil.sendKey(it)
        }
        startWifiAdb()
    }

    private fun createNotification() {
        val notification = Notification.Builder(this, "233")
            .setContentTitle("KeyBoardService")
            //.setSmallIcon(R.drawable.ic_icon)
            .setContentIntent(Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            })
            .build()
        startForeground(233, notification)
    }

    private fun startWifiAdb() {
        Shell.cmd("setprop service.adb.tcp.port 5555").exec()
        Shell.cmd("stop adbd").exec()
        Shell.cmd("start adbd").exec()
    }
}