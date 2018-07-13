package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.EventHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class EventAdapter(
        mContext: Context,
        private var eventList: List<Event>,
        private var petList : List<Pet>? )
    : RecyclerView.Adapter<EventHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/EVENT"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val mBinder = ItemEventBinding.inflate(inflater, parent, false)
        return EventHolder(mBinder)
    }

    fun setEventList(newList: List<Event>){
        Log.i(TAG, "Altering consultations list.")
        this.eventList = newList
        notifyDataSetChanged()
    }

    fun setPetList(newList: List<Pet>) {
        Log.i(TAG, "Altering pets list.")
        this.petList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = eventList[position]
        val pet = petList?.find { it.id == event.pet }
        holder.bind(event, pet)

    }

    override fun getItemCount() = eventList.size
}
