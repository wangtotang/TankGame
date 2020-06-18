package com.tang.game.model

import com.tang.game.Config
import com.tang.game.business.*
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

class EnemyTank(override var x: Int, override var y: Int) : Moveable, AutoMoveable, Blockable, AutoShot, Sufferable, Destroyable {

    override var blood: Int = 2

    override var direction: Direction = Direction.DOWN

    override val speed: Int = 6

    override var wrongDirection: Direction? = null

    var lastShotTime: Long = 0L
    val shotFrequency: Long = 600L

    var lastMoveTime: Long = 0L
    val moveFrequency: Long = 50L

    override fun autoMove() {

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastMoveTime < moveFrequency) return
        lastMoveTime = currentTime


        if (direction == wrongDirection) {
            direction = randomDirection()
            return
        }

        when (direction) {
            Direction.UP -> y -= speed
            Direction.RIGHT -> x += speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
        }

        if (x <= 0) x = 0
        if (y <= 0) y = 0
        if (x >= Config.gameWidth - width) x = Config.gameWidth - width
        if (y >= Config.gameHeight - height) y = Config.gameHeight - height
    }

    fun randomDirection(): Direction {
        val random = Random.nextInt(4)
        val nextDirection = when (random) {
            0 -> Direction.UP
            1 -> Direction.RIGHT
            2 -> Direction.DOWN
            3 -> Direction.LEFT
            else -> Direction.UP
        }
        if (nextDirection == wrongDirection) {
            return randomDirection()
        }
        return nextDirection
    }

    override fun autoShot(): View? {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastShotTime < shotFrequency) return null
        lastShotTime = currentTime

        return Bullet(this, direction) { bulletWidth, bulletHeight ->
            var bulletX = 0
            var bulletY = 0

            when (direction) {
                Direction.UP -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y + height - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = x - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = x + width - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
            }

            Pair(bulletX ,bulletY)
        }
    }

    override fun draw() {
        val imagePath = when (direction) {
            Direction.UP -> "imgs/enemy_u.gif"
            Direction.RIGHT -> "imgs/enemy_r.gif"
            Direction.DOWN -> "imgs/enemy_d.gif"
            Direction.LEFT -> "imgs/enemy_l.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    override fun isDestroyed(): Boolean = blood <= 0

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        if (attackable.owner is EnemyTank){
            return null
        }
        blood -= attackable.attack
        Composer.play("audios/hit.wav")
        return arrayOf(Blast{ blastWidth, blastHeight ->
            val blastX = x - (blastWidth - width) / 2
            val blastY = y - (blastHeight - height) / 2
            Pair(blastX, blastY)
        })
    }

}