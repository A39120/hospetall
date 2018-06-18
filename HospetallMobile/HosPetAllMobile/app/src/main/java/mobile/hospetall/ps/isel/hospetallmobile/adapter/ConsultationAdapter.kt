package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

/**
 * Adapter to fill a Recycler View with information on
 * consultations list.
 * @param list: List of [Consultation] to fill the view
 * @param mContext: activity context that uses the adapter
 */
class ConsultationAdapter(
        private val mContext: Context,
        private val list:Array<Consultation>,
        private val petList: Array<Pet>? = null)
    : ProcedureAdapter<Consultation>(mContext, list) {
    companion object {
        private const val TAG = "HPA/ADAPTER/CONSULT"
    }


    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        Log.i(TAG, "Binding consultation to activity.")
        val procedure = list[position]
        val pet = petList?.find { procedure.petUri?.equals(it.uri)?:false }
        itemHolder.bind(
                procedure,
                pet?.name,
                listener = View.OnClickListener{
                    //ConsultationActivity.start(mContext, procedure.id)
                    ConsultationActivity.start(mContext, procedure, pet)
                }
        )
    }
}