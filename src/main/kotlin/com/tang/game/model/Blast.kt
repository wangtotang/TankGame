package com.tang.game.model

import com.tang.game.business.Destroyable
import org.itheima.kotlin.game.core.Painter


class Blast(create: (width: Int, height: Int) -> Pair<Int, Int>) : Destroyable {

    override var x: Int = 0
    override var y: Int = 0
    override val width: Int = 136
    override val height: Int = 107
    private val imagePaths = arrayListOf<String>()
    private var index = 0

    init {
        (1..8).forEach {
            imagePaths.add("imgs/blast${it}.gif")
        }
        val pair = create.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun isDestroyed(): Boolean = index >= imagePaths.size

    override fun draw() {
        val i = index % imagePaths.size
        Painter.drawImage(imagePaths[i], x, y)
        index++
    }

}