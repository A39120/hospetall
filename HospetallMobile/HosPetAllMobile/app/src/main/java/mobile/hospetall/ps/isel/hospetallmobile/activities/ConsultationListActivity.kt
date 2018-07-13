package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.AbstractListFragment.Companion.SHOW_PET
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.ConsultationListFragment
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ConsultationListActivity : BaseActivity() {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun start(context: Context){
            val int = Intent(context, ConsultationListActivity::class.java)
            context.startActivity(int)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = Bundle()
        args.putBoolean(SHOW_PET, true)
        val uri = UriUtils.getConsultationListUri().build().toString()
        args.putString(ConsultationListFragment.URI, uri)


        val listFragment : Fragment = ConsultationListFragment()
        listFragment.arguments = args
        val fManager = this.supportFragmentManager.beginTransaction()
        fManager.add(android.R.id.content, listFragment)
        fManager.commit()
    }
}
