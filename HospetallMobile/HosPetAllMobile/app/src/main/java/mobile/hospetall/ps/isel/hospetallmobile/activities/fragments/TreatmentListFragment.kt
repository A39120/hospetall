package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.TreatmentListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentListFragment : AbstractListFragment() {

    companion object {
        const val TITLE = R.string.treatment
        const val URI = "treatment_list_uri"
        const val TAG = "HPA/FRAG/TREATMENT_LIST"
    }

    private lateinit var viewModel : TreatmentListViewModel
    private lateinit var recycler: RecyclerView
    private lateinit var adapter : TreatmentAdapter


    override fun callbackInfo(view: View) {
        val show = arguments?.getBoolean(SHOW_PET)?:false
        adapt(
                viewModel.getTreatmentList()?.value?: ArrayList(),
                viewModel.getPetList()?.value
        )

        viewModel.getTreatmentList()?.observe(this, Observer {
            if(it != null) adapter.setData(it)
        })

        if(show)
            viewModel.getPetList()?.observe(this, Observer {
                if(it != null) adapter.setAuxData(it)
            })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val uri = arguments?.getString(URI)
        if(uri == null) {
            //TODO: Error Handler
            return
        }

        viewModel = ViewModelProviders.of(this).get(TreatmentListViewModel::class.java)
        viewModel.init(uri)
    }

    /**
     * Adapts information to the recycler view.
     * @param treatments: Binds information from this array;
     * @param pets: Helper array that will bind information
     * about a certain pet;
     */
    private fun adapt(treatments: List<Treatment>, pets : List<Pet>?) {
        recycler = view!!.findViewById(R.id.procedure_list)
        adapter = TreatmentAdapter(context!!, treatments.toTypedArray(), pets?.toTypedArray())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle() = TITLE

}