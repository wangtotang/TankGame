package com.tang.game.model

import com.tang.game.Config

interface View {

    val x: Int
    val y: Int

    val width: Int
        get() = Config.block
    val height: Int
        get() = Config.block

    fun draw()

}