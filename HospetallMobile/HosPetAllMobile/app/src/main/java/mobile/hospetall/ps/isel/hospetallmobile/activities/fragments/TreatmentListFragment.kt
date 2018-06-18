package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnTreatmentListListener

class TreatmentListFragment : AbstractProcedureListFragment<Treatment>() {
    companion object {
        const val TITLE = R.string.treatment
        const val TAG = "HPA/FRAG/TREATMENT_LIST"
    }

    override fun callbackInfo(view: View) {
        (activity as OnTreatmentListListener).onTreatmentList(
                {
                    Log.i(TAG, "Binding treatment list to TreatmentFragment.")
                    val recycler = view.findViewById<RecyclerView>(R.id.procedure_list)
                    recycler.adapter = TreatmentAdapter(context!!, it.toTypedArray())
                    recycler.layoutManager = LinearLayoutManager(context!!)
                }
        )

    }

    override fun getTitle() = TITLE

}