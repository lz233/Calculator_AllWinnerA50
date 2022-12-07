package moe.lz233.allwinnera50.util

import android.annotation.SuppressLint
import android.app.StatusBarManager
import android.hardware.input.InputManager
import android.media.AudioManager
import android.view.InputEvent
import android.view.KeyEvent
import com.topjohnwu.superuser.Shell
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.logic.meta.KeyBoardEvent

object InputManagerUtil {
    const val MODE_NUMBER = 1
    const val MODE_DPAD = 2

    val inputManager = InputManager::class.java.getMethod("getInstance")
        .invoke(InputManager::class.java) as InputManager
    var statusBarExpanded = false
    var mode = MODE_NUMBER

    @SuppressLint("NewApi")
    fun sendKey(keyBoardEvent: KeyBoardEvent) {
        val androidKeyCode = when (mode) {
            MODE_NUMBER -> when (keyBoardEvent.keycode.toInt()) {
                1 -> {
                    App.audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
                    -1
                }
                2 -> {
                    App.audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
                    -1
                }
                3 -> KeyEvent.KEYCODE_NUMPAD_DOT
                4 -> KeyEvent.KEYCODE_0
                5 -> KeyEvent.KEYCODE_0
                6 -> KeyEvent.KEYCODE_1
                7 -> KeyEvent.KEYCODE_2
                8 -> KeyEvent.KEYCODE_3
                9 -> KeyEvent.KEYCODE_4
                10 -> KeyEvent.KEYCODE_5
                11 -> KeyEvent.KEYCODE_6
                12 -> KeyEvent.KEYCODE_7
                13 -> KeyEvent.KEYCODE_8
                14 -> KeyEvent.KEYCODE_9
                15 -> {
                    if (statusBarExpanded)
                        StatusBarManager::class.java.getMethod("collapsePanels").invoke(App.statusBarManager)
                    else
                        StatusBarManager::class.java.getMethod("expandNotificationsPanel").invoke(App.statusBarManager)
                    statusBarExpanded = !statusBarExpanded
                    -1
                }
                16 -> {
                    mode = MODE_DPAD
                    LogUtil.toast("Dpad Mode")
                    -1
                }
                17 -> KeyEvent.KEYCODE_DEL
                19 -> KeyEvent.KEYCODE_CLEAR
                20 -> KeyEvent.KEYCODE_BACK
                21 -> KeyEvent.KEYCODE_HOME
                22 -> KeyEvent.KEYCODE_APP_SWITCH
                26 -> KeyEvent.KEYCODE_NUMPAD_MULTIPLY
                27 -> KeyEvent.KEYCODE_NUMPAD_ADD
                29 -> KeyEvent.KEYCODE_NUMPAD_DIVIDE
                30 -> KeyEvent.KEYCODE_NUMPAD_SUBTRACT
                31 -> KeyEvent.KEYCODE_ENTER
                else -> 0
            }
            MODE_DPAD -> when (keyBoardEvent.keycode.toInt()) {
                1 -> {
                    App.audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
                    -1
                }
                2 -> {
                    App.audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
                    -1
                }
                7 -> KeyEvent.KEYCODE_DPAD_DOWN
                9 -> KeyEvent.KEYCODE_DPAD_LEFT
                10 -> KeyEvent.KEYCODE_DPAD_CENTER
                11 -> KeyEvent.KEYCODE_DPAD_RIGHT
                13 -> KeyEvent.KEYCODE_DPAD_UP
                15 -> {
                    if (statusBarExpanded)
                        StatusBarManager::class.java.getMethod("collapsePanels").invoke(App.statusBarManager)
                    else
                        StatusBarManager::class.java.getMethod("expandNotificationsPanel").invoke(App.statusBarManager)
                    statusBarExpanded = !statusBarExpanded
                    -1
                }
                16 -> {
                    mode = MODE_NUMBER
                    LogUtil.toast("Number Mode")
                    -1
                }
                20 -> KeyEvent.KEYCODE_BACK
                21 -> KeyEvent.KEYCODE_HOME
                22 -> KeyEvent.KEYCODE_APP_SWITCH
                31 -> KeyEvent.KEYCODE_ENTER
                else -> 0
            }
            else -> -1
        }
        val repeatTime = when (keyBoardEvent.keycode.toInt()) {
            4 -> 2
            else -> 1
        }
        val duringTime = when (keyBoardEvent.keycode.toInt()) {
            else -> 20L
        }
        LogUtil._d("androidKeyCode $androidKeyCode")
        if (androidKeyCode != -1) sendAndroidKey(keyCode = androidKeyCode, repeat = repeatTime, during = duringTime)
    }

    fun sendAndroidKey(keyCode: Int, repeat: Int = 0, during: Long = 20, downTime: Long = System.currentTimeMillis(), eventTime: Long = System.currentTimeMillis()) {
        repeat(repeat) {
            InputManager::class.java.getMethod("injectInputEvent", InputEvent::class.java, Int::class.javaPrimitiveType)
                .invoke(inputManager, KeyEvent(downTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0), 0)
            InputManager::class.java.getMethod("injectInputEvent", InputEvent::class.java, Int::class.javaPrimitiveType)
                .invoke(inputManager, KeyEvent(downTime + during, eventTime + during, KeyEvent.ACTION_UP, keyCode, 0), 0)
        }
    }
}