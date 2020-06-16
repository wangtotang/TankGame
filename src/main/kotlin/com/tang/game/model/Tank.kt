package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.Blockable
import com.tang.game.business.Moveable
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) : Moveable {

    override var direction: Direction = Direction.UP

    override val speed: Int = 6

    var wrongDirection: Direction? = null

    override fun move(direction: Direction) {

        if (direction == wrongDirection) {
            return
        }

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

        if (x <= 0) x = 0
        if (y <= 0) y = 0
        if (x >= Config.gameWidth - width) x = Config.gameWidth - width
        if (y >= Config.gameHeight - height) y = Config.gameHeight - height
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

    override fun willCollision(blockable: Blockable): Direction? {

        var tempX = x
        var tempY = y
        when (direction) {
            Direction.UP -> tempY -= speed
            Direction.RIGHT -> tempX += speed
            Direction.DOWN -> tempY += speed
            Direction.LEFT -> tempX -= speed
        }

        val isCollision = when {
            tempY >= blockable.y + blockable.height -> false
            tempY <= blockable.y - height -> false
            tempX <= blockable.x - width -> false
            tempX >= blockable.x + blockable.width -> false
            else -> true
        }

        return if (isCollision) direction else null

    }

    override fun notifyCollision(direction: Direction?) {
        wrongDirection = direction
    }

    fun shot(): Bullet {
        return Bullet(direction) { bulletWidth, bulletHeight ->
            var bulletX = 0
            var bulletY = 0

            when (direction) {
                Direction.UP -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y + height - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = x - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = x + width - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
            }

            Pair(bulletX ,bulletY)
        }
    }

}