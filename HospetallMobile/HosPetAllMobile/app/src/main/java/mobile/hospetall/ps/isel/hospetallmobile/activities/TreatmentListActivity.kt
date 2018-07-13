package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.AbstractListFragment.Companion.SHOW_PET
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.TreatmentListFragment
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.TreatmentListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils


class TreatmentListActivity : BaseActivity() {

    companion object {
        const val TAG = "HPA/ACTIVITY/CONS_LIST"

        fun start(context: Context){
            val int = Intent(context, TreatmentListActivity::class.java)
            context.startActivity(int)
        }
    }

    private lateinit var viewModel : TreatmentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        bundle.putBoolean(SHOW_PET, true)

        val uri = UriUtils.getPetsTreatmentUri(getId()).build().toString()
        bundle.putString(TreatmentListFragment.URI, uri)

        val listFragment : Fragment = TreatmentListFragment()
        listFragment.arguments = bundle
        val fManager = this.supportFragmentManager.beginTransaction()
        fManager.add(android.R.id.content, listFragment)
        fManager.commit()
    }
}
