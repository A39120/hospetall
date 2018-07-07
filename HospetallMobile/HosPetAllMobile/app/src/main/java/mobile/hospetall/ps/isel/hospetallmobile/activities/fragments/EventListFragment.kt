package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.AddEventActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.EventAdapter
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnEventListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener

class EventListFragment : AbstractListFragment() {
    companion object {
        const val TAG = "HPA/FRAG/EVENT_LST"
        const val TITLE = "TITLE"
        const val TYPE = "TYPE"
        const val FUTURE_EVENTS = 0
        const val PAST_EVENTS = 1
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val arg = arguments?.getInt(TYPE)?: FUTURE_EVENTS
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
        (activity as OnEventListListener).onEventListListener({
            val events = it
            (activity as OnPetListListener).onPetList {
                val recycler = view.findViewById<RecyclerView>(R.id.procedure_list)
                recycler.adapter = EventAdapter(context!!, events!!, it)
                recycler.layoutManager = LinearLayoutManager(context!!)
            }
        }, arguments?.getInt(TYPE)?: FUTURE_EVENTS)
    }

    override fun getTitle(): Int = arguments?.getInt(TITLE)?:-1

}