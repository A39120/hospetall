package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemProcedureBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure


class ProcedureItemHolder(private val binder: ItemProcedureBinding):  AbstractHolder<ItemProcedureBinding>(binder) {

    fun bind(proc: Procedure, petName: String? = null, operation : String? = null){
        binder.setDate(proc.date)
        petName?.apply{binder.setPet(this)}
        operation?.apply {
            binder.operation = this
        }

        binder.procedure.setOnClickListener {

        }

        binder.executePendingBindings()
    }
}