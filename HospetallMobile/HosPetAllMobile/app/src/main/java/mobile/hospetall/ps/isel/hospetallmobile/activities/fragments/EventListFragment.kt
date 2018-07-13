package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.AddEventActivity
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ScheduleViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.EventAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class EventListFragment : AbstractListFragment() {
    companion object {
        const val TAG = "HPA/FRAG/EVENT_LST"
        const val TITLE = "TITLE"
        const val TYPE = "TYPE"
        const val FUTURE_EVENTS = 0
        const val PAST_EVENTS = 1
    }

    private lateinit var viewModel : ScheduleViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter : EventAdapter? = null

    private var arg : Int = 2

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arg = arguments?.getInt(TYPE)?: 2
        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        viewModel.init(arg)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = if(arg == PAST_EVENTS) {
            inflater.inflate(R.layout.activity_list, container, false)
        } else {
            val add = inflater.inflate(R.layout.activity_list_with_add, container, false)
            val button = add.findViewById<FloatingActionButton>(R.id.add_button)
            button.setOnClickListener {
                AddEventActivity.start(context!!)
            }
            add
        }

        callbackInfo(rootView)
        return rootView
    }

    override fun callbackInfo(view: View) {
        viewModel.getEvents()?.observe(this, Observer {
            it?.apply{
                if(adapter == null)
                    createAdapter(view, it)
                else
                    adapter!!.setEventList(it)
            }
        })

        viewModel.getPets()?.observe(this, Observer {
            it?.apply {
                adapter?.run {
                    this.setPetList(it)
                }
            }
        })
    }

    private fun createAdapter(view: View, events : List<Event>) {
        recyclerView = view.findViewById(R.id.procedure_list)
        adapter = EventAdapter(context!!, events, null)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle(): Int = arguments?.getInt(TITLE)?:-1

}