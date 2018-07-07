package mobile.hospetall.ps.isel.hospetallmobile.adapter.view

import android.content.Context
import android.widget.ArrayAdapter
import mobile.hospetall.ps.isel.hospetallmobile.R

object PeriodSpinnerObject {

    fun getAdapter(context : Context) : ArrayAdapter<CharSequence> {
        val adapter =
                ArrayAdapter.createFromResource(context, R.array.periods ,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

}