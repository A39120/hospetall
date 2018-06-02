package mobile.hospetall.ps.isel.hospetallmobile.view

import android.content.Context
import android.graphics.Typeface


object FontManager {
    val ROOT = "font/"
    val FONTAWESOME = ROOT + "faRegular400.ttf"

    fun getTypeface(context: Context, font: String): Typeface {
        return Typeface.createFromAsset(context.getAssets(), font)
    }
}