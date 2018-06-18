package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.TreatmentActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentAdapter(
        private val mContext: Context,
        private val list:Array<Treatment>,
        private val petList: Array<Pet>? = null)
    : ProcedureAdapter<Treatment>(mContext, list) {
    companion object {
        private const val TAG = "HPA/ADAPTER/CONSULT"
    }

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list[position]
        val pet = petList?.find{ procedure.petUri?.equals(it.uri)?:false }
        itemHolder.bind(procedure,
                pet?.uri,
                listener = View.OnClickListener{
                    TreatmentActivity.start(mContext, procedure, pet)
        })
    }
}