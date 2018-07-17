package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.EventHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import java.util.*

class EventAdapter(
        private val mContext: Context,
        private val eventList: MutableList<Event> = mutableListOf(),
        private val petList : MutableList<Pet> = mutableListOf())
    : RecyclerView.Adapter<EventHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/EVENT"
    }

    private val inflater = LayoutInflater.from(mContext)
    private val dateFormat  by lazy { getDateFormat(mContext) }
    private val timeFormat by lazy { getTimeFormat(mContext) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val mBinder = ItemEventBinding.inflate(inflater, parent, false)
        return EventHolder(mBinder)
    }

    fun setEventList(newList: List<Event>){
        Log.i(TAG, "Altering events list.")
        val res = DiffUtil.calculateDiff(EventDiffCallback(eventList, newList))
        eventList.clear()
        eventList.addAll(newList)
        res.dispatchUpdatesTo(this)
    }

    fun setPetList(newList: List<Pet>) {
        Log.i(TAG, "Altering pets list.")
        val res = DiffUtil.calculateDiff(BaseDiffUtilCallback(petList, newList))
        petList.clear()
        petList.addAll(newList)
        res.dispatchUpdatesTo(this)

    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val event = eventList[position]
        val pet = petList.find { it.id == event.pet }
        val date = Date(event.timedate)

        val dateString = dateFormat.format(date)
        val timeString = timeFormat.format(date)
        holder.setOnClick(mContext, event.id)
        holder.bind(event, "$dateString, $timeString", pet)
    }

    override fun getItemCount() = eventList.size

    private class EventDiffCallback(private val old: List<Event>, private val new: List<Event>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                old[oldItemPosition].id == new[newItemPosition].id

        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            old[oldItemPosition] == new[newItemPosition]

    }
}
