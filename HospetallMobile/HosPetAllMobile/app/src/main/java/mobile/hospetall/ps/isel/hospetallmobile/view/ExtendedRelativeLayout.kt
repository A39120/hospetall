package mobile.hospetall.ps.isel.hospetallmobile.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class ExtendedRelativeLayout : RelativeLayout {

    var afterVisible : () -> Unit = {}
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if(visibility== View.VISIBLE)
            afterVisible()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


}
