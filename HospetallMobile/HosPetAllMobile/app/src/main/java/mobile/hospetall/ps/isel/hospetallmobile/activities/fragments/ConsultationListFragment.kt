package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val uri = arguments?.getString(URI)
        uri?.let {
            viewModel = ViewModelProviders.of(this).get(ConsultationListViewModel::class.java)
            viewModel.init(uri)
        }
    }

    /**
     * Sets up the view model observers for the
     * [Consultation] list and [Pet] list.
     */
    override fun callbackInfo(view: View) {
        viewModel.getConsultationList()?.observe(this, Observer {
            it?.toTypedArray()?.let {
                if(adapter == null)
                    createAdapter(view, it)
                else
                    adapter?.setConsultationList(it)
        }})

        viewModel.getPetList()?.observe(this, Observer {
            it?.run {
                adapter?.setPetList(it.toTypedArray())
            }
        })

    }

    private lateinit var viewModel : ConsultationListViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter: ConsultationAdapter? = null

    /**
     * Adapts information to the recycler view.
     * @param consultationArray: Binds information from this
     * array;
     * @param petArray: Helper array that will bind information
     * about a certain pet;
     */
    private fun createAdapter(view: View, consultationArray: Array<Consultation>, petArray: Array<Pet>? = null) {
        recyclerView = view.findViewById(R.id.procedure_list)
        adapter = ConsultationAdapter(context!!, consultationArray, petArray)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }


    override fun getTitle() = TITLE
}