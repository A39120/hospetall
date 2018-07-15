package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.activities.TreatmentActivity
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentAdapter(
        mContext: Context,
         list: MutableList<Treatment> = mutableListOf(),
        petList: MutableList<Pet> = ArrayList())
    : ProcedureAdapter<Treatment>(mContext, list, petList) {

    override fun goToActivity(context: Context, id: Int) {
        TreatmentActivity.start(context, id)
    }
}