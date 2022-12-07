package moe.lz233.allwinnera50.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.quicksettings.Tile
import com.topjohnwu.superuser.Shell
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.R
import moe.lz233.allwinnera50.logic.dao.ConfigDao
import moe.lz233.allwinnera50.ui.main.MainActivity
import moe.lz233.allwinnera50.ui.process.ProcessServiceActivity
import moe.lz233.allwinnera50.util.InputManagerUtil
import moe.lz233.allwinnera50.util.LogUtil
import moe.lz233.allwinnera50.util.SerialPortUtil

class KeyBoardService : android.service.quicksettings.TileService() {

    var start = true

    override fun onCreate() {
        super.onCreate()
        createNotification("started")
        SerialPortUtil.keyBoardListenerList.add {
            LogUtil._d(start)
            if (start) InputManagerUtil.sendKey(it)
        }
        //execShell()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        InputManagerUtil.mode = ConfigDao.keyboardMode
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil._d("onDestroy")
        SerialPortUtil.keyBoardListenerList.clear()
        App.context.startActivity(Intent(App.context, ProcessServiceActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_ACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        when (qsTile.state) {
            Tile.STATE_UNAVAILABLE -> {
                onDestroy()
                qsTile.state = Tile.STATE_ACTIVE
            }
            Tile.STATE_INACTIVE -> {
                start = true
                createNotification("started")
                qsTile.state = Tile.STATE_ACTIVE
            }
            Tile.STATE_ACTIVE -> {
                start = false
                createNotification("paused")
                qsTile.state = Tile.STATE_INACTIVE
            }
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = if (start) {
            Tile.STATE_ACTIVE
        } else {
            Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    private fun createNotification(content: String) {
        val notification = Notification.Builder(this, "233")
            .setContentTitle("KeyboardService")
            .setContentText(content)
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
        //LogUtil.toast(Shell.cmd("su dumpsys activity top | grep ACTIVITY").exec().out[0])
    }
}