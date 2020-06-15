package com.tang.game.business

import com.tang.game.model.Direction

interface Moveable {

    val direction: Direction

    val speed: Int

    fun move(direction: Direction)

}