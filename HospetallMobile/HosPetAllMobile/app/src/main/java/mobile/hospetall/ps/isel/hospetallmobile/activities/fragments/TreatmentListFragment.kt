package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ProcedureAdapter
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentListFragment : AbstractProcedureListFragment<Treatment>() {
    companion object {
        const val TITLE = R.string.treatment
        const val TAG = "HPA/FRAG/TREATMENT_LIST"
    }

    override fun callbackInfo(view: View) {

        (activity as PetActivity).onTreatmentList(
                Response.Listener {
                    Log.i(TAG, "Binding treatment list to TreatmentFragment.")
                    val recycler = view.findViewById<RecyclerView>(R.id.procedure_list)
                    recycler.adapter = TreatmentAdapter(context!!, it.toTypedArray())
                    recycler.layoutManager = LinearLayoutManager(context!!)
                }
        )

    }

    override fun getTitle() = TITLE

}