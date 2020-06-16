package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.AutoMoveable
import com.tang.game.business.Destroyable
import org.itheima.kotlin.game.core.Painter

class Bullet(override val direction: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>) : AutoMoveable, Destroyable{

    override var x: Int = 0
    override var y: Int = 0
    override val speed: Int = 20
    override var width: Int = 17
    override var height: Int = 17

    private val imagePath = "imgs/bullet.gif"

    init {

        val size = Painter.size(imagePath)
        width = size[0]
        height = size[1]
        val pair = create.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun autoMove() {
        when (direction) {
            Direction.UP -> y -= speed
            Direction.RIGHT -> x += speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
        }
    }

    override fun isDestroyed(): Boolean {
        return when {
            x + width <= 0 -> true
            y + height <= 0 -> true
            x >= Config.gameWidth -> true
            y >= Config.gameHeight -> true
            else -> false
        }
    }

    override fun draw() {
        Painter.drawImage(imagePath, x, y)
    }

}