package com.tang.game.model

import com.tang.game.business.Attackable
import com.tang.game.business.Blockable
import com.tang.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Camp(override val x: Int, override val y: Int) : Blockable, Sufferable {

    override val blood: Int = 12
    override val width: Int = 40
    override val height: Int = 40

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }

    override fun draw() {
        Painter.drawImage("imgs/star.gif", x - width / 2, y - height )
    }
}