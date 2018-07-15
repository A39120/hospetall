package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationActivity
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
        list: MutableList<Consultation> = mutableListOf(),
        petList: MutableList<Pet> = mutableListOf())
    : ProcedureAdapter<Consultation>(mContext, list, petList) {

    override fun goToActivity(context: Context, id: Int) {
        ConsultationActivity.start(context, id)
    }
}

