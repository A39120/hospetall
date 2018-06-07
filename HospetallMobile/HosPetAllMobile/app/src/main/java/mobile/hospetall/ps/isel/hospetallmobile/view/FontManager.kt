package mobile.hospetall.ps.isel.hospetallmobile.view

import android.content.Context
import android.graphics.Typeface


object FontManager {
    private const val ROOT = "font/"
    const val FONTAWESOME = ROOT + "faRegular400.ttf"

    fun getTypeface(context: Context, font: String): Typeface {
        return Typeface.createFromAsset(context.assets, font)
    }
}