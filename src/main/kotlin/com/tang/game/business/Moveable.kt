package com.tang.game.business

import com.tang.game.model.Direction
import com.tang.game.model.View

interface Moveable : View {

    val direction: Direction

    val speed: Int

    fun move(direction: Direction)

    fun willCollision(blockable: Blockable): Direction?

    fun notifyCollision(direction: Direction?)

}