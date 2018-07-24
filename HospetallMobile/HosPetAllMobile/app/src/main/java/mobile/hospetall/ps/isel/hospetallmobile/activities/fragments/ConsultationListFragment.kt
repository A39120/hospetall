package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ConsultationAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnConsultationListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener

class ConsultationListFragment : AbstractListFragment() {

    companion object {
        const val TITLE = R.string.consultation
        const val TAG = "HPA/FRAG/CONSULT_LST"
    }

    override fun callbackInfo(view: View) {
        (activity as OnConsultationListListener).onConsultationList(
                {
                    Log.i(TAG, "Binding consultation list to ConsultationFragment.")
                    val showPet = arguments?.getBoolean(SHOW_PET)?: false
                    val consultationArray = it.toTypedArray()
                    if(showPet){
                        Log.i(TAG, "Getting information for pets to accompany the consultation list.")
                        (activity as OnPetListListener).onPetList {
                            adapt(consultationArray, it.toTypedArray())
                        }
                    }else
                        adapt(consultationArray)
                }
        )
    }



    /**
     * Adapts information to the recycler view.
     * @param consultationArray: Binds information from this
     * array;
     * @param petArray: Helper array that will bind information
     * about a certain pet;
     */
    private fun adapt(consultationArray: Array<Consultation>, petArray: Array<Pet>? = null) {
        val recycler = view!!.findViewById<RecyclerView>(R.id.procedure_list)
        recycler.adapter = ConsultationAdapter(context!!, consultationArray, petArray)
        recycler.layoutManager = LinearLayoutManager(context!!)
    }


    override fun getTitle() = TITLE
}