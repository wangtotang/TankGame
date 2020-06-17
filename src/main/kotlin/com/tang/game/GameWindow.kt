package com.tang.game

import com.tang.game.business.*
import com.tang.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window("坦克大战", "imgs/logo.jpg", Config.gameWidth, Config.gameHeight) {

    val views = CopyOnWriteArrayList<View>()
    lateinit var tank: Tank

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
                    '敌' -> views.add(EnemyTank(x, y))
                }
                columns++
            }
            rows++
        }

        tank = Tank(10 * Config.block, 12 * Config.block)
        views.add(tank)
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {

        when (event.code) {
            KeyCode.UP -> tank.move(Direction.UP)
            KeyCode.RIGHT -> tank.move(Direction.RIGHT)
            KeyCode.DOWN -> tank.move(Direction.DOWN)
            KeyCode.LEFT -> tank.move(Direction.LEFT)
            KeyCode.R -> views.add(tank.shot())
        }

    }

    override fun onRefresh() {

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

        views.filterIsInstance<Destroyable>().forEach {
            if (it.isDestroyed()) views.remove(it)
        }

        views.filterIsInstance<Attackable>().forEach { attack ->
            views.filterIsInstance<Sufferable>().forEach sufferTag@ { suffer ->
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

    }
}