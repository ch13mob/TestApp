package com.ch13mob.testapp.common.extension

import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ch13mob.testapp.R

fun ImageView.loadWithKeepRatio(url: String) {
    doOnPreDraw {
        Glide.with(context)
            .load(url)
            .apply(centerCropWithPlaceholderOptions)
            .override(measuredWidth, measuredHeight)
            .into(this)
    }
}

private val centerCropWithPlaceholderOptions: RequestOptions
    get() = RequestOptions()
        .error(R.drawable.ic_image_placeholder)
        .placeholder(R.drawable.ic_image_placeholder)
        .centerCrop()