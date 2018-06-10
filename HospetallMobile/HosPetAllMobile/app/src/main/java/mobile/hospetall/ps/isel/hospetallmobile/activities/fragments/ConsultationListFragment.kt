package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ConsultationAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnConsultationListListener

class ConsultationListFragment
    : AbstractProcedureListFragment<Consultation>() {

    companion object {
        const val TITLE = R.string.consultation
        const val TAG = "HPA/FRAG/CONSULT_LST"
        const val SHOW_PET = "show_bet"
    }

    override fun callbackInfo(view: View) {
        (activity as OnConsultationListListener).onConsultationList(
                Response.Listener {
                    Log.i(TAG, "Binding consultation list to ConsultationFragment.")
                    val recycler = view.findViewById<RecyclerView>(R.id.procedure_list)
                    val showPet = (arguments?.getBoolean(SHOW_PET, false))?: false

                    recycler.adapter = ConsultationAdapter(context!!, it.toTypedArray())
                    recycler.layoutManager = LinearLayoutManager(context!!)
                }
        )

    }

    override fun getTitle() = TITLE
}