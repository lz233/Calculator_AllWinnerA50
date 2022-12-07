package moe.lz233.allwinnera50.util

import android.os.Message
import android.util.SparseArray
import com.kongqw.serialportlibrary.SerialPortManager
import com.kongqw.serialportlibrary.listener.OnOpenSerialPortListener
import com.kongqw.serialportlibrary.listener.OnSerialPortDataListener
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.logic.meta.KeyBoardEvent
import java.io.File
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.jvm.functions.FunctionN

object SerialPortUtil {
    private const val BAUDRATE = 115200;
    private val serialPortManager = SerialPortManager()
    private val DEVICE_PATH = File("dev/ttyS4")
    val keyArray = SparseArray<String>()
    val keyBoardListenerList = mutableListOf<(KeyBoardEvent) -> Unit>()

    init {
        serialPortManager.setOnOpenSerialPortListener(object : OnOpenSerialPortListener {
            override fun onSuccess(device: File) {
                LogUtil._d("OnOpenSerialPortListener onSuccess ${device.path}")
            }

            override fun onFail(device: File, status: OnOpenSerialPortListener.Status) {
                LogUtil._d("OnOpenSerialPortListener onSuccess ${device.path} ${status.ordinal}")
            }
        })
        serialPortManager.setOnSerialPortDataListener(object : OnSerialPortDataListener {
            override fun onDataReceived(bytes: ByteArray) {
                LogUtil._d("onDataReceived ${bytes[1].toInt()} keyBoardListenerList ${keyBoardListenerList.size}")
                keyBoardListenerList.forEach {
                    it(KeyBoardEvent(bytes[1], keyArray.get(bytes[1].toInt())))
                }
            }

            override fun onDataSent(bytes: ByteArray) {

            }
        })
        initKeyArray()
    }

    fun openSerialPort() = serialPortManager.openSerialPort(DEVICE_PATH, BAUDRATE)

    fun closeSerialPort() = serialPortManager.closeSerialPort()

    fun sendScreenOffMessage() = serialPortManager.sendBytes(byteArrayOf(-86, KeyBoardEvent.KEYCODE_SLEEP, -33, 85))

    fun addAKeyBoardListener(function: (KeyBoardEvent) -> Unit) = keyBoardListenerList.add(function)

    private fun initKeyArray() {
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance(
            Locale.Builder()
                .setLocale(App.context.resources.configuration.locales[0])
                .setUnicodeLocaleKeyword("nu", "latn")
                .build()
        )
        val zeroDigit = decimalFormatSymbols.zeroDigit
        keyArray.apply {
            put(1, "F1")
            put(2, "F2")
            put(3, decimalFormatSymbols.decimalSeparator.toString())
            put(4, "00")
            put(5, zeroDigit.toString())
            put(6, (zeroDigit + 1).toString())
            put(7, (zeroDigit + 2).toString())
            put(8, (zeroDigit + 3).toString())
            put(9, (zeroDigit + 4).toString())
            put(10, (zeroDigit + 5).toString())
            put(11, (zeroDigit + 6).toString())
            put(12, (zeroDigit + 7).toString())
            put(13, (zeroDigit + '\b'.code).toString())
            put(14, (zeroDigit + '\t'.code).toString())
            put(15, "GT")
            put(16, "pos_or_nag")
            put(17, "DEL")
            put(18, "CCE")
            put(19, "CA")
            put(20, "MU")
            put(21, "CM")
            put(22, "RM")
            put(23, "M-")
            put(24, "M+")
            put(25, "%")
            put(26, "\u00d7")
            put(27, "+")
            put(28, "\u221a")
            put(29, "\u00f7")
            put(30, "-")
            put(31, "=")
        }
    }
}