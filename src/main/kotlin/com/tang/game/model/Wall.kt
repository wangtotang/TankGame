package com.tang.game.model

import com.tang.game.business.Blockable
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) : Blockable {

    override fun draw() {
        Painter.drawImage("imgs/wall.gif", x, y)
    }

}