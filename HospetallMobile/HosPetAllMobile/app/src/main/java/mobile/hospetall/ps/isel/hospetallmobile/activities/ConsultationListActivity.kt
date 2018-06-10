package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.ConsultationListFragment
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.getId
import mobile.hospetall.ps.isel.hospetallmobile.getPetsConsultationsUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnConsultationListListener

class ConsultationListActivity :
        BaseActivity(),
        OnConsultationListListener {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun startActivity(context: Context){
            val int = Intent(context, ConsultationListActivity::class.java)
            context.startActivity(int)
        }
    }

    private val consultationAccess by lazy { ConsultationAccess(application.requestQueue) }

    override fun onConsultationList(list: Response.Listener<List<Consultation>>) {
            val uri = getPetsConsultationsUri(resources, getId()).build().toString()
            consultationAccess.getList(
                    uri,
                    list,
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                    }
            )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listFragment : Fragment = ConsultationListFragment()

        val fManager = this.supportFragmentManager.beginTransaction()
        fManager.add(android.R.id.content, listFragment)
        fManager.commit()
    }
}
