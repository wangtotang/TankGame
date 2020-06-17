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

    fun checkCollision(x1: Int, y1: Int, w1: Int, h1: Int, x2: Int, y2: Int, w2: Int, h2: Int): Boolean {
        return when {
            y1 >= y2 + h2 -> false
            y1 <= y2 - h1 -> false
            x1 <= x2 - w1 -> false
            x1 >= x2 + w2 -> false
            else -> true
        }
    }

}