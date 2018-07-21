package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.TreatmentListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter

class TreatmentListFragment : AbstractListFragment() {

    companion object {
        const val TITLE = R.string.treatment
        const val URI = "treatment_list_uri"
        const val TAG = "HPA/FRAG/TREATMENT_LIST"
    }

    private lateinit var viewModel : TreatmentListViewModel
    private lateinit var recycler: RecyclerView
    private var adapter : TreatmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Initiating Treatment View Model.")
        viewModel = ViewModelProviders.of(this).get(TreatmentListViewModel::class.java)
    }

    override fun callbackInfo(view: View) {
        val uri = arguments?.getString(URI)
        Log.i(TAG, "Getting $uri passed from activity.")
        uri?.apply { viewModel.init(this) }

        Log.i(TAG, "Observing for changes.")
        val show = arguments?.getBoolean(SHOW_PET)?:false
        viewModel.getTreatmentList()?.observe(this, Observer {
            Log.i(TAG, "Observer called for Treatment List.")
            it?.let {
                if(it.isEmpty())viewModel.update()
                adapter!!.setProcedure(it)
            }
        })
        if(show)
            viewModel.getPetList()?.observe(this, Observer {
                Log.i(TAG, "Observer called for Pet List.")
                it?.let{
                    if(it.isEmpty())viewModel.update()
                    adapter?.setPets(it)
                }
            })
    }

    /**
     * Adapts information to the recycler view.
     */
    override fun createAdapter(view: View) {
        Log.i(TAG, "Adapting recycler adapter.")
        recycler = view.findViewById(R.id.procedure_list)
        adapter = TreatmentAdapter(context!!)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle() = TITLE

}