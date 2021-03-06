package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.AddEventActivity
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ScheduleViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.EventAdapter

class EventListFragment : BaseFragment() {
    companion object {
        const val TAG = "HPA/FRAG/EVENT_LST"
        const val TITLE = "TITLE"
        const val TYPE = "TYPE"

        const val PERIODIC_EVENTS = 0
        const val FUTURE_EVENTS = 1
        const val PAST_EVENTS = 2
    }

    private lateinit var viewModel : ScheduleViewModel
    private lateinit var recyclerView: RecyclerView
    private var adapter : EventAdapter? = null

    private var arg : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Instantiating EventListViewModel.")
        viewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        arg = arguments?.getInt(TYPE, -1)?: -1
        val rootView =
                when(arg){
                    PAST_EVENTS -> inflater.inflate(R.layout.activity_list, container, false)
                    FUTURE_EVENTS, PERIODIC_EVENTS -> {
                        val add = inflater.inflate(R.layout.activity_list_with_add, container, false)
                        val button = add.findViewById<FloatingActionButton>(R.id.add_button)
                        button.setOnClickListener {
                            AddEventActivity.start(context!!)
                        }
                        add
                    }
                    else -> throw IllegalArgumentException()
                }

        createAdapter(rootView, arg)
        callbackInfo(rootView, arg)
        return rootView
    }

    private fun callbackInfo(view: View, arg : Int) {
        Log.i(TAG, "Passing bundle args to view model.")
        viewModel.init(arg)

        Log.i(TAG, "Starting observers for EventListViewModel for type $arg.")
        viewModel.getEvents()?.observe(this, Observer {
            it?.apply{
                Log.i(TAG, "Event list changed.")
                adapter?.setEventList(it)
            }
        })

        viewModel.getPets()?.observe(this, Observer {
            it?.apply {
                Log.i(TAG, "Pet list changed.")
                adapter?.setPetList(it)
            }
        })
    }

    private fun createAdapter(view: View, type : Int) {
        Log.i(TAG, "Creating adapter for recycler view.")
        recyclerView = view.findViewById(R.id.list)
        adapter = EventAdapter(context!!, type = type)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!)
    }

    override fun getTitle(): Int = arguments?.getInt(TITLE)?:-1

}