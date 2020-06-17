package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.AutoMoveable
import com.tang.game.business.Blockable
import com.tang.game.business.Moveable
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

class EnemyTank(override var x: Int, override var y: Int) : Moveable, AutoMoveable, Blockable {

    override var direction: Direction = Direction.DOWN

    override val speed: Int = 6

    override var wrongDirection: Direction? = null

    override fun autoMove() {
        if (direction == wrongDirection) {
            direction = randomDirection()
            return
        }

        when (direction) {
            Direction.UP -> y -= speed
            Direction.RIGHT -> x += speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
        }

        if (x <= 0) x = 0
        if (y <= 0) y = 0
        if (x >= Config.gameWidth - width) x = Config.gameWidth - width
        if (y >= Config.gameHeight - height) y = Config.gameHeight - height
    }

    fun randomDirection(): Direction {
        val random = Random.nextInt(4)
        val nextDirection = when (random) {
            0 -> Direction.UP
            1 -> Direction.RIGHT
            2 -> Direction.DOWN
            3 -> Direction.LEFT
            else -> Direction.UP
        }
        if (nextDirection == wrongDirection) {
            return randomDirection()
        }
        return nextDirection
    }

    override fun draw() {
        val imagePath = when (direction) {
            Direction.UP -> "imgs/enemy_u.gif"
            Direction.RIGHT -> "imgs/enemy_r.gif"
            Direction.DOWN -> "imgs/enemy_d.gif"
            Direction.LEFT -> "imgs/enemy_l.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

}