package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.EventHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class EventAdapter(
        private val mContext: Context,
        private val eventList: List<Event>,
        private val petList : List<Pet>)
    : RecyclerView.Adapter<EventHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/EVENT"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val mBinder = ItemEventBinding.inflate(inflater, parent, false)
        return EventHolder(mBinder)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = eventList[position]
        val pet = petList.find { it.id == event.pet }
        holder.bind(event, pet)

    }

    override fun getItemCount() = eventList.size
}
