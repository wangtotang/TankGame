package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.Attackable
import com.tang.game.business.Blockable
import com.tang.game.business.Destroyable
import com.tang.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Camp(override var x: Int, override var y: Int) : Blockable, Sufferable, Destroyable {

    override var blood: Int = 9
    override var width: Int = 120
    override var height: Int = 90
    private lateinit var imagePath: String
    private val StarX = Config.gameWidth / 2 - 20
    private val StarY = Config.gameHeight - 40

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attack
        if ((blood == 6) or (blood == 3)) {
            return arrayOf(Blast { blastWidth, blastHeight ->
                Pair(x - (blastWidth - width) / 2, y - (blastHeight - height))
            })
        }
        return null
    }

    override fun isDestroyed(): Boolean = blood <= 0

    override fun showDestroyed(): View? {
        return Blast { blastWidth, blastHeight ->
            Pair(x - (blastWidth - width) / 2, y - (blastHeight - height))
        }
    }

    override fun draw() {
        Painter.drawImage("imgs/star.gif", StarX, StarY)
        if (blood <= 3) {
            x = StarX
            y = StarY
            width = 40
            height = 40
        } else if (blood <= 6) {
            x = Config.gameWidth / 2 - 45
            y = Config.gameHeight - 60
            width = 90
            height = 60
            imagePath = "imgs/wall_s.gif"
            Painter.drawImage(imagePath, x, y)
            Painter.drawImage(imagePath, x + 15, y)
            Painter.drawImage(imagePath, x + 30, y)
            Painter.drawImage(imagePath, x + 45, y)
            Painter.drawImage(imagePath, x + 60, y)
            Painter.drawImage(imagePath, x + 75, y)

            Painter.drawImage(imagePath, x, y + 15)
            Painter.drawImage(imagePath, x, y + 30)
            Painter.drawImage(imagePath, x, y + 45)

            Painter.drawImage(imagePath, x + 75, y + 15)
            Painter.drawImage(imagePath, x + 75, y + 30)
            Painter.drawImage(imagePath, x + 75, y + 45)
        } else if (blood <= 9) {
            imagePath = "imgs/steel_s.gif"
            Painter.drawImage(imagePath, x, y)
            Painter.drawImage(imagePath, x + 30, y)
            Painter.drawImage(imagePath, x + 60, y)
            Painter.drawImage(imagePath, x + 90, y)

            Painter.drawImage(imagePath, x, y + 30)
            Painter.drawImage(imagePath, x, y + 60)

            Painter.drawImage(imagePath, x + 90, y + 30)
            Painter.drawImage(imagePath, x + 90, y + 60)
        }

    }

}