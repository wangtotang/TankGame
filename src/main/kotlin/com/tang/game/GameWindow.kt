package com.tang.game

import com.tang.game.business.*
import com.tang.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window("坦克大战", "imgs/logo.jpg", Config.gameWidth, Config.gameHeight) {

    private val views = CopyOnWriteArrayList<View>()
    private lateinit var tank: Tank

    private val enemyBornLocation = arrayListOf<Pair<Int, Int>>()
    private var enemyTotalSize = 20
    private val enemyActiveSize = 6
    private var bornIndex = 0
    private var gameOver = false

    override fun onCreate() {
        val stream = javaClass.getResourceAsStream("/maps/1.map")
        val reader = BufferedReader(InputStreamReader(stream, "utf-8"))

        val lines = reader.readLines()

        var rows: Int = 0
        lines.forEach { line ->
            var columns: Int = 0
            line.toCharArray().forEach { ch ->
                val x = columns * Config.block
                val y = rows * Config.block
                when (ch) {
                    '砖' -> views.add(Wall(x, y))
                    '铁' -> views.add(Steel(x, y))
                    '水' -> views.add(Water(x, y))
                    '草' -> views.add(Grass(x, y))
                    '敌' -> enemyBornLocation.add(Pair(x, y))
                }
                columns++
            }
            rows++
        }

        tank = Tank(10 * Config.block, 12 * Config.block)
        views.add(tank)

        val camp = Camp(Config.gameWidth / 2 - 60, Config.gameHeight - 90)
        views.add(camp)
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (gameOver) return

        when (event.code) {
            KeyCode.UP -> tank.move(Direction.UP)
            KeyCode.RIGHT -> tank.move(Direction.RIGHT)
            KeyCode.DOWN -> tank.move(Direction.DOWN)
            KeyCode.LEFT -> tank.move(Direction.LEFT)
            KeyCode.R -> views.add(tank.shot())
        }

    }

    override fun onRefresh() {

        views.filterIsInstance<Destroyable>().forEach {
            if (it.isDestroyed()) {
                views.remove(it)
                var destroyed = it.showDestroyed()
                destroyed?.let {
                    views.add(destroyed)
                }
                if (it is EnemyTank) {
                    enemyTotalSize--
                }
            }
        }

        if (gameOver) return

        if (views.filterIsInstance<Camp>().size <= 0 || enemyTotalSize <= 0) {
            gameOver = true
        }

        views.filterIsInstance<Moveable>().forEach { move ->

            var direction: Direction? = null

            views.filter { (it is Blockable) and (it != move) }.forEach blockTag@{ block ->

                move.willCollision(block as Blockable)?.let {
                    direction = it
                    return@blockTag
                }

            }

            move.notifyCollision(direction)

        }

        views.filterIsInstance<AutoMoveable>().forEach {
            it.autoMove()
        }

        views.filterIsInstance<Attackable>().forEach { attack ->
            views.filter { (it is Sufferable) and (attack.owner != it) and (attack != it)}.forEach sufferTag@ { suffer ->

                suffer as Sufferable
                if (attack.isCollision(suffer)) {
                    attack.notifyAttack(suffer)
                    val blast = suffer.notifySuffer(attack)
                    blast?.let {
                        views.addAll(blast)
                    }
                    return@sufferTag
                }
            }
        }

        views.filterIsInstance<AutoShot>().forEach {
            var shot = it.autoShot()
            shot?.let {
                views.add(shot)
            }
        }

        if(views.filterIsInstance<EnemyTank>().size < enemyActiveSize && enemyTotalSize >= enemyActiveSize) {
            views.add(bornEnemyTank())
        }

    }

    fun bornEnemyTank(): EnemyTank {
        bornIndex++
        val i = bornIndex % enemyActiveSize
        val tank = EnemyTank(enemyBornLocation[i].first, enemyBornLocation[i].second)
        views.filterIsInstance<EnemyTank>().forEach {
            it.willCollision(tank)?.let {
                return bornEnemyTank()
            }
        }
        return tank
    }
}