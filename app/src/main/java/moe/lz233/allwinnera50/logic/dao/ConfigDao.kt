package moe.lz233.allwinnera50.logic.dao

import moe.lz233.allwinnera50.App

object ConfigDao {
    var keyboardMode: Int
        get() = App.sp.getInt("keyboardMode", 0)
        set(value) = App.editor.putInt("keyboardMode", value).apply()
}