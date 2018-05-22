package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

class ConsultationAdapter(baseContext : Context,
                          private val consultations: Array<Consultation>)
    : BaseAdapter<Consultation>(baseContext, R.layout.procedure_item, consultations) {

    override fun saveToHolder(rowView: View) {

    }

    override fun manipulateItem(rowView: View): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun onExpand(holder: ConsultationHolder, consultation: Consultation) {
        Log.i("ACCESS/PET_LIST", "Showing pet detail.")
    }

    private fun onMinimize(holder: ConsultationHolder) {
        Log.i("ACCESS/PET_LIST", "Minimizing pet detail.")
    }

    data class ConsultationHolder(val id: Int)
}
