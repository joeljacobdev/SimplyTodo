package com.pcforgeek.simplytodo.utils

import java.util.concurrent.Executors

/**
 * Created by
 *      JOEL JACOB(@pcforgeek)
 *       on 28/7/18.
 */



private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}