package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.ConsultationListFragment
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.getId
import mobile.hospetall.ps.isel.hospetallmobile.getPetsTreatmentUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnTreatmentListListener


class TreatmentListActivity :
        BaseActivity(),
        OnTreatmentListListener {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun startActivity(context: Context){
            val int = Intent(context, TreatmentListActivity::class.java)
            context.startActivity(int)
        }
    }

    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue) }

    override fun onTreatmentList(onList: Response.Listener<List<Treatment>>) {
        val uri = getPetsTreatmentUri(resources, getId()).build().toString()
        treatmentAccess.getList(
                uri,
                onList,
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
