package com.tang.game.business

import com.tang.game.model.Direction
import com.tang.game.model.View

interface AutoMoveable : View {

    val direction: Direction

    val speed: Int

    fun autoMove()

}