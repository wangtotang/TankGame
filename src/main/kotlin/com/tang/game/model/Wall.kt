package com.tang.game.model

import com.tang.game.business.Attackable
import com.tang.game.business.Blockable
import com.tang.game.business.Destroyable
import com.tang.game.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) : Blockable, Sufferable, Destroyable {

    override var blood: Int = 3

    override fun isDestroyed(): Boolean {
       return blood <= 0
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attack
        Composer.play("audios/hit.wav")
        return arrayOf(Blast{ blastWidth, blastHeight ->
            val blastX = x - (blastWidth - width) / 2
            val blastY = y - (blastHeight - height) / 2
            Pair(blastX, blastY)
        })
    }

    override fun draw() {
        Painter.drawImage("imgs/wall.gif", x, y)
    }

}