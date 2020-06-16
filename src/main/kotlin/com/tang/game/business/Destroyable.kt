package com.tang.game.business

import com.tang.game.model.View

interface Destroyable : View {

    fun isDestroyed(): Boolean

}