package moe.lz233.allwinnera50.ui.main

import android.content.Intent
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.StrictMode
import android.view.InputEvent
import android.view.KeyEvent
import com.kongqw.serialportlibrary.SerialPortManager
import com.topjohnwu.superuser.Shell
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.util.LogUtil
import kotlinx.coroutines.launch
import moe.lz233.allwinnera50.databinding.ActivityMainBinding
import moe.lz233.allwinnera50.service.KeyBoardService
import moe.lz233.allwinnera50.ui.BaseActivity
import moe.lz233.allwinnera50.util.InputManagerUtil
import moe.lz233.allwinnera50.util.SerialPortUtil
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        setContentView(binding.root)

        SerialPortUtil.keyBoardListenerList.add {
            binding.textview.text = "${it.keycode} ${it.keyValue}"
        }
        startForegroundService(Intent(this,KeyBoardService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        SerialPortUtil.keyBoardListenerList.removeLast()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //binding.textview.text = keyCode.toString()
        return super.onKeyDown(keyCode, event)
    }
}