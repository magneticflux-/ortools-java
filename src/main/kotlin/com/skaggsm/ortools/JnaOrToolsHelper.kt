package com.skaggsm.ortools

import com.sun.jna.NativeLibrary

/**
 * Created by Mitchell Skaggs on 4/11/2020.
 */
object JnaOrToolsHelper {
    /**
     * Call before using Google OR-Tools.
     */
    @JvmStatic
    fun loadLibrary() {
        System.load(NativeLibrary.getInstance("jniortools").file.canonicalPath)
    }
}
