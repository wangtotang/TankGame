package com.tang.game.model

import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) : View {

    override fun draw() {
        Painter.drawImage("imgs/wall.gif", x, y)
    }

}