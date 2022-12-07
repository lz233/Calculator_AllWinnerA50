package moe.lz233.allwinnera50.ui.process

import android.content.Intent
import android.os.Bundle
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.service.KeyBoardService
import moe.lz233.allwinnera50.ui.BaseActivity

class ProcessServiceActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startForegroundService(Intent(App.context, KeyBoardService::class.java))
        finish()
    }
}