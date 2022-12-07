package moe.lz233.allwinnera50.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.topjohnwu.superuser.Shell
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.R
import moe.lz233.allwinnera50.logic.dao.ConfigDao
import moe.lz233.allwinnera50.ui.main.MainActivity
import moe.lz233.allwinnera50.ui.process.ProcessServiceActivity
import moe.lz233.allwinnera50.util.InputManagerUtil
import moe.lz233.allwinnera50.util.SerialPortUtil

class KeyBoardService : Service() {

    var stopService = false

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        createNotification()
        SerialPortUtil.keyBoardListenerList.add {
            InputManagerUtil.sendKey(it)
        }
        //execShell()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        stopService = intent.getBooleanExtra("stopService", false)
        if (stopService) stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!stopService) {
            SerialPortUtil.keyBoardListenerList.clear()
            App.context.startActivity(Intent(App.context, ProcessServiceActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

    private fun createNotification() {
        val notification = Notification.Builder(this, "233")
            .setContentTitle("KeyboardService")
            .setSmallIcon(R.drawable.ic_keyboard)
            .setContentIntent(Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            })
            .build()
        startForeground(233, notification)
    }

    private fun execShell() {
        Shell.cmd("setprop service.adb.tcp.port 5555").exec()
        Shell.cmd("stop adbd").exec()
        Shell.cmd("start adbd").exec()
        Shell.cmd("wm overscan 0,0,0,-58").exec()
    }
}