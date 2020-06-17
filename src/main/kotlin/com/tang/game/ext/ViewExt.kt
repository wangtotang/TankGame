package com.tang.game.ext

import com.tang.game.model.View

fun View.checkCollision(view1: View, view2: View): Boolean {
    return checkCollision(view1.x, view1.y, view1.width, view1.height, view2.x, view2.y, view2.width, view2.height)
}