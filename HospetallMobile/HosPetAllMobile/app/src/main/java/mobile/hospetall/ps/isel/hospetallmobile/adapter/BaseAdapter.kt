package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import mobile.hospetall.ps.isel.hospetallmobile.R

abstract class BaseAdapter<T>(baseContext: Context, resId: Int, objects: Array<T>)
    :ArrayAdapter<T>(baseContext, resId, objects)
{

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = view ?: inflater.inflate(R.layout.procedure_item, parent, false)

        if (view == null) {
            saveToHolder(rowView)
        }
        return manipulateItem(rowView)
    }

    abstract fun saveToHolder(rowView: View)
    abstract fun manipulateItem(rowView: View) : View

}