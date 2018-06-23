package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.AbstractProcedureListFragment.Companion.SHOW_PET
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.TreatmentListFragment
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.database
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnTreatmentListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils


class TreatmentListActivity :
        BaseActivity(),
        OnTreatmentListListener,
        OnPetListListener {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun start(context: Context){
            val int = Intent(context, TreatmentListActivity::class.java)
            context.startActivity(int)
        }
    }

    private val petAccess by lazy { PetAccess(application.requestQueue, application.database)  }
    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue, application.database) }

    /**
     * Implemented method of [OnTreatmentListListener]
     * called when you have a treatment list.
     * @param onList: callback called when you have the list
     * of treatments;
     */
    override fun onTreatmentList(onList: (List<Treatment>) -> Unit) {
        val uri = UriUtils.getPetsTreatmentUri(resources, getId()).build().toString()
        treatmentAccess.getList(
                uri,
                Response.Listener(onList),
                Response.ErrorListener {
                    Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                }
        )
    }

    /**
     * Implemented listener that acts according to the the
     * uri for the pets of a certain client.
     * @param listener: callback called when you have the list
     * of pets
     */
    override fun onPetList(listener: (List<Pet>) -> Unit) {
        val uri = UriUtils.getClientsPetsUri(resources, getId()).build().toString()
        petAccess.getList(
                uri,
                Response.Listener(listener),
                Response.ErrorListener {
                    Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        val listFragment : Fragment = TreatmentListFragment()
        bundle.putBoolean(SHOW_PET, true)
        listFragment.arguments = bundle

        val fManager = this.supportFragmentManager.beginTransaction()
        fManager.add(android.R.id.content, listFragment)
        fManager.commit()
    }
}
