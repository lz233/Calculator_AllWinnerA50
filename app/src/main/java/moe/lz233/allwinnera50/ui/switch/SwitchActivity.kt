package moe.lz233.allwinnera50.ui.switch

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.logic.dao.ConfigDao
import moe.lz233.allwinnera50.service.KeyBoardService
import moe.lz233.allwinnera50.ui.BaseActivity
import moe.lz233.allwinnera50.util.InputManagerUtil
import moe.lz233.allwinnera50.util.LogUtil

class SwitchActivity : BaseActivity() {
    private val modeArray = arrayOf("Number Mode", "DPad Mode", "Maimemo Mode")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Switch Keyboard Mode")
            .setSingleChoiceItems(modeArray, ConfigDao.keyboardMode) { dialog, which ->
                LogUtil._d(which)
                ConfigDao.keyboardMode = which
                startForegroundService(Intent(this, KeyBoardService::class.java))
                dialog.dismiss()
            }
            .setOnDismissListener {
                finish()
            }
            .show()
    }
}