package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.Attackable
import com.tang.game.business.AutoMoveable
import com.tang.game.business.Destroyable
import com.tang.game.business.Sufferable
import com.tang.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

class Bullet(override val direction: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>) :
    AutoMoveable, Destroyable, Attackable{

    override var x: Int = 0
    override var y: Int = 0
    override val speed: Int = 20
    override var width: Int = 17
    override var height: Int = 17
    override val attack: Int = 1

    private val imagePath = "imgs/bullet.gif"
    private var isDestroyed = false

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
        if (isDestroyed) return true
        return when {
            x + width <= 0 -> true
            y + height <= 0 -> true
            x >= Config.gameWidth -> true
            y >= Config.gameHeight -> true
            else -> false
        }
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(this, sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        isDestroyed = true
    }

    override fun draw() {
        Painter.drawImage(imagePath, x, y)
    }

}