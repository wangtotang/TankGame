package com.tang.game.model

import com.tang.game.business.Moveable
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) : View, Moveable {

    override var direction: Direction = Direction.UP

    override val speed: Int = 8

    override fun move(direction: Direction) {
        if (this.direction == direction) {
            when (direction) {
                Direction.UP -> y -= speed
                Direction.RIGHT -> x += speed
                Direction.DOWN -> y += speed
                Direction.LEFT -> x -= speed
            }
        } else {
            this.direction = direction
        }
    }

    override fun draw() {
        val imagePath = when (direction) {
            Direction.UP -> "imgs/tank_u.gif"
            Direction.RIGHT -> "imgs/tank_r.gif"
            Direction.DOWN -> "imgs/tank_d.gif"
            Direction.LEFT -> "imgs/tank_l.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

}