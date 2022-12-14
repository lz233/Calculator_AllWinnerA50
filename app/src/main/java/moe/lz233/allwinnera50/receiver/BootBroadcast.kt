package moe.lz233.allwinnera50.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.service.KeyBoardService
import moe.lz233.allwinnera50.ui.process.ProcessServiceActivity
import kotlin.system.exitProcess

class BootBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> context.startActivity(Intent(App.context, ProcessServiceActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}