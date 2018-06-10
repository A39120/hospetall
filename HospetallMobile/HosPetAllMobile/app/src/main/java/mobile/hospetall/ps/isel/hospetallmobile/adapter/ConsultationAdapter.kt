package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

class ConsultationAdapter(
        private val mContext: Context,
        private val list:Array<Consultation>)
    : ProcedureAdapter<Consultation>(mContext, list) {
    companion object {
        private const val TAG = "HPA/ADAPTER/CONSULT"
    }

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        Log.i(TAG, "Binding consultation to activity.")
        val procedure = list[position]
        itemHolder.bind(
                procedure,
                listener = View.OnClickListener{
                    ConsultationActivity.startActivity(mContext, procedure.id)
                }
        )
    }
}