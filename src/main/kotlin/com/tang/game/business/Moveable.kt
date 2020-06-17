package com.tang.game.business

import com.tang.game.Config
import com.tang.game.model.Direction
import com.tang.game.model.View

interface Moveable : View {

    val direction: Direction

    val speed: Int

    var wrongDirection: Direction?

    fun willCollision(blockable: Blockable): Direction? {
        var tempX = x
        var tempY = y
        when (direction) {
            Direction.UP -> tempY -= speed
            Direction.RIGHT -> tempX += speed
            Direction.DOWN -> tempY += speed
            Direction.LEFT -> tempX -= speed
        }

        if ((tempX < 0) || (tempY < 0) || (tempX > Config.gameWidth - width) || (tempY > Config.gameHeight - height)) return direction

        return if (checkCollision(tempX, tempY, width, height, blockable.x, blockable.y, blockable.width, blockable.height)) direction else null
    }

    fun notifyCollision(direction: Direction?) {
        wrongDirection = direction
    }

}