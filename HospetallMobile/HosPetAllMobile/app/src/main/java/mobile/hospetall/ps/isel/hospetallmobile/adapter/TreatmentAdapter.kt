package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.TreatmentActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentAdapter(
        private val mContext: Context,
        private var list:Array<Treatment>,
        private var petList: Array<Pet>? = null)
    : ProcedureAdapter<Treatment>(mContext, list) {
    companion object {
        private const val TAG = "HPA/ADAPTER/CONSULT"
    }

    fun setData(newData : List<Treatment>) {
        this.list = newData.toTypedArray()
        notifyDataSetChanged()
    }

    fun setAuxData(newData : List<Pet>){
        this.petList = newData.toTypedArray()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list[position]
        val pet = petList?.find{ procedure.petUri == it.uri}
        itemHolder.bind(procedure,
                pet?.name,
                listener = View.OnClickListener{ TreatmentActivity.start(mContext, procedure.id) })
    }
}