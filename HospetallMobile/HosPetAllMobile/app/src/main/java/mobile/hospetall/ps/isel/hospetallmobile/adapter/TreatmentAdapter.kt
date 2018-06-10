package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationActivity
import mobile.hospetall.ps.isel.hospetallmobile.activities.TreatmentActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentAdapter(
        private val mContext: Context,
        private val list:Array<Treatment>)
    : ProcedureAdapter<Treatment>(mContext, list) {
    companion object {
        private const val TAG = "HPA/ADAPTER/CONSULT"
    }

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list[position]
        itemHolder.bind(procedure, listener = View.OnClickListener{
            TreatmentActivity.startActivity(mContext, procedure.id)
        })
    }
}