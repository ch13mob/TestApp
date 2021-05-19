package com.ch13mob.testapp.common.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.ch13mob.testapp.R

class FavouriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        if (isInEditMode) {
            updateFavouriteState(true)
        }
    }

    fun updateFavouriteState(isFavourite: Boolean) {
        val favoriteIconRes = when {
            isFavourite -> R.drawable.ic_favorite
            else -> R.drawable.ic_favorite_border
        }

        this.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                favoriteIconRes
            )
        )
    }
}