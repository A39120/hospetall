package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnTreatmentListListener

class TreatmentListFragment : AbstractListFragment() {
    companion object {
        const val TITLE = R.string.treatment
        const val TAG = "HPA/FRAG/TREATMENT_LIST"
    }

    override fun callbackInfo(view: View) {
        (activity as OnTreatmentListListener).onTreatmentList {
                    val treatArr = it.toTypedArray()
                    Log.i(TAG, "Binding treatment list to TreatmentFragment.")
                    val show =arguments?.getBoolean(SHOW_PET)?:false
                    if(show)
                        (activity as OnPetListListener).onPetList {
                            adapt(treatArr, it.toTypedArray())
                        }
                    else adapt(treatArr)
                }

    }


    /**
     * Adapts information to the recycler view.
     * @param treatmentArray: Binds information from this
     * array;
     * @param petArray: Helper array that will bind information
     * about a certain pet;
     */
    private fun adapt(treatmentArray: Array<Treatment>, petArray: Array<Pet>? = null) {
        val recycler = view!!.findViewById<RecyclerView>(R.id.procedure_list)
        recycler.adapter = TreatmentAdapter(context!!, treatmentArray, petArray)
        recycler.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle() = TITLE

}