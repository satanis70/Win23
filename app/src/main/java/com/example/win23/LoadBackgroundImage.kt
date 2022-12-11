package com.example.win23

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso

object LoadBackgroundImage {
    fun setImage(context: Context, constraintLayout: ConstraintLayout){
        Picasso.with(context).load(URL_BACKGROUND).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                constraintLayout.background = BitmapDrawable(bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }
}