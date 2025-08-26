package com.wespot.post.nudge.server_driven

import com.wespot.view.color.Color

data class Gradation(
    val startColor: Color,
    val endColor: Color,
    val angle: Int,
)
