package com.tang.game

import com.tang.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

class GameWindow : Window("坦克大战", "imgs/logo.jpg", Config.gameWidth, Config.gameHeight) {

    val views = arrayListOf<View>()
    lateinit var tank: Tank

    override fun onCreate() {
        val stream = javaClass.getResourceAsStream("/maps/1.map")
        val reader = BufferedReader(InputStreamReader(stream, "utf-8"))

        val lines = reader.readLines()

        var rows: Int = 0
        lines.forEach { line ->
            var columns: Int = 0
            line.toCharArray().forEach { ch ->
                when (ch) {
                    '砖' -> views.add(Wall(columns * Config.block, rows * Config.block))
                    '铁' -> views.add(Steel(columns * Config.block, rows * Config.block))
                    '水' -> views.add(Water(columns * Config.block, rows * Config.block))
                    '草' -> views.add(Grass(columns * Config.block, rows * Config.block))
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
        }

    }

    override fun onRefresh() {
    }
}