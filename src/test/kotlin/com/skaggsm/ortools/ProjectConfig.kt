package com.skaggsm.ortools

import io.kotest.core.config.AbstractProjectConfig

/**
 * Created by Mitchell Skaggs on 5/18/2019.
 */
object ProjectConfig : AbstractProjectConfig() {
    override val parallelism: Int = Runtime.getRuntime().availableProcessors()
}
