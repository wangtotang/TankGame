package com.tang.game.model

import com.tang.game.business.Attackable
import com.tang.game.business.Blockable
import com.tang.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int) : Blockable, Sufferable {

    override val blood: Int = 1

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }

    override fun draw() {
        Painter.drawImage("imgs/steel.gif", x, y)
    }

}