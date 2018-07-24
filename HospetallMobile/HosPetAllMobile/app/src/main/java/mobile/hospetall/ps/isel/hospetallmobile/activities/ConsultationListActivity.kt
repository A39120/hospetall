package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.AbstractListFragment.Companion.SHOW_PET
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.ConsultationListFragment
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnConsultationListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ConsultationListActivity :
        BaseActivity(),
        OnConsultationListListener,
        OnPetListListener {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun start(context: Context){
            val int = Intent(context, ConsultationListActivity::class.java)
            context.startActivity(int)
        }
    }

    private val petAccess by lazy { PetAccess() }
    private val consultationAccess by lazy { ConsultationAccess() }


    override fun onConsultationList(list: (List<Consultation>) -> Unit) {
            val uri = UriUtils.getPetsConsultationsUri(getId()).build().toString()
            consultationAccess.getList(
                    uri,
                    Response.Listener(list),
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                    }
            )
    }

    override fun onPetList(listener: (List<Pet>) -> Unit) {
        val uri = UriUtils.getClientsPetsUri(getId()).toString()
        petAccess.getList(
                uri,
                Response.Listener(listener),
                Response.ErrorListener {
                    Log.e(PetActivity.TAG, "Failed to get pet list from $uri: ${it.message}")
                }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = Bundle()
        args.putBoolean(SHOW_PET, true)

        val listFragment : Fragment = ConsultationListFragment()
        listFragment.arguments = args

        val fManager = this.supportFragmentManager.beginTransaction()
        fManager.add(android.R.id.content, listFragment)
        fManager.commit()
    }
}
