package com.wespot.common.dto.view

import com.wespot.post.nudge.server_driven.Gradation
import com.wespot.view.color.Color

data class GradationResponse(
    val startColor: Color,
    val endColor: Color,
    val angle: Int,
) {
    companion object {

        fun from(gradation: Gradation): GradationResponse {
            return GradationResponse(
                startColor = gradation.startColor,
                endColor = gradation.endColor,
                angle = gradation.angle
            )
        }

    }

}
