package com.ch13mob.testapp.common.extension

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.absoluteValue

fun AppBarLayout.addOffsetListener(listener: (Float) -> Unit) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { o, verticalOffset ->
        val alpha = 1 - verticalOffset.toFloat().absoluteValue.div(o.totalScrollRange)
        listener(alpha)
    })
}