package com.smartdev.vkcup2.util

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing

object AnimateDuration {
    const val Fast = 150
    const val Long = 300
    const val ExtraLong = 500
}

object AnimateEasing{
    val EaseInBack: Easing = CubicBezierEasing(0.36f, 0f, 0.66f, -0.56f)
    val EaseOutBack: Easing = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1f)
}
