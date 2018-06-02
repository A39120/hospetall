package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemProcedureBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure


class ProcedureItemHolder(private val binder: ItemProcedureBinding):  AbstractHolder<ItemProcedureBinding>(binder) {

    fun bind(proc: Procedure, petName: String? = null, operation : String? = null){
        binder.setDate(proc.date)
        petName?.apply{binder.setPet(this)}
        operation?.apply {
            binder.operation = this
        }
        binder.executePendingBindings()
    }

    /*val date: TextView
    val pet: TextView
    val procedure: TextView

    constructor(itemView: View) : super(itemView) {
        date = itemView.findViewById<TextView>(R.id.date)
        pet = itemView.findViewById<TextView>(R.id.pet)
        procedure = itemView.findViewById<TextView>(R.id.procedure)
    }*/
}