package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ConsultationListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ConsultationAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class ConsultationListFragment : AbstractListFragment() {

    companion object {
        const val TITLE = R.string.consultation
        const val TAG = "HPA/FRAG/CONSULT_LST"
        const val URI = "consultation_list_uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsultationListViewModel::class.java)
    }

    /**
     * Sets up the view model observers for the
     * [Consultation] list and [Pet] list.
     */
    override fun callbackInfo(view: View) {
        Log.i(TAG, "Getting argument uri from Activity.")
        val uri = arguments?.getString(URI)
        uri?.let { viewModel.init(it) }

        Log.i(TAG, "Starting observers for ConsultationListViewModel.")

        viewModel.getConsultationList()?.observe(this, Observer {
            Log.i(TAG, "Consultation List has changed, updating view.")
            it?.let {
                if(it.isEmpty()) viewModel.update()
                adapter?.setProcedure(it)
            }
        })

        viewModel.getPetList()?.observe(this, Observer {
            Log.i(TAG, "Observer called for pet list, updating view.")
            it?.let { adapter?.setPets(it) }
        })

    }

    private lateinit var viewModel : ConsultationListViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: ConsultationAdapter? = null

    /**
     * Adapts information to the recycler view.
     */
    override fun createAdapter(view: View) {
        recyclerView = view.findViewById(R.id.procedure_list)
        adapter = ConsultationAdapter(context!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle() = TITLE
}