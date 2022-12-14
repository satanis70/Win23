package com.example.win23

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object LoadBackgroundImage {
    fun setImage(context: Context, constraintLayout: ConstraintLayout) {
        Glide.with(context)
            .asDrawable()
            .load("http://49.12.202.175/win22/background.png")
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    constraintLayout.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}