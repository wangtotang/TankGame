package com.tang.game.business

import com.tang.game.model.View

interface Attackable : View {

    val attack: Int

    fun isCollision(sufferable: Sufferable): Boolean

    fun notifyAttack(sufferable: Sufferable)

}