package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R


class ProcedureHolder:  RecyclerView.ViewHolder {

    val date: TextView
    val pet: TextView
    val procedure: TextView

    constructor(itemView: View) : super(itemView) {
        date = itemView.findViewById<TextView>(R.id.date)
        pet = itemView.findViewById<TextView>(R.id.pet)
        procedure = itemView.findViewById<TextView>(R.id.procedure)
    }
}