package com.djekgrif.fastcleaner.ui

import android.content.res.Resources

/**
 * Created by djek-grif on 10/19/17.
 */
object UiUtils {

    fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }
}