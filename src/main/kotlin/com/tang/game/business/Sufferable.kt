package com.tang.game.business

import com.tang.game.model.View

interface Sufferable : View {

    val blood: Int

    fun notifySuffer(attackable: Attackable): Array<View>?

}