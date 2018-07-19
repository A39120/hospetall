package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.EventHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemNonPeriodicEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import java.util.*

class EventAdapter(
        private val mContext: Context,
        private val eventList: MutableList<Event> = mutableListOf(),
        private val petList : MutableList<Pet> = mutableListOf(),
        private val type: Int)
    : RecyclerView.Adapter<EventHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/EVENT"
    }

    private val inflater = LayoutInflater.from(mContext)
    private val dateFormat  by lazy { getDateFormat(mContext) }
    private val timeFormat by lazy { getTimeFormat(mContext) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val mBinder = ItemNonPeriodicEventBinding.inflate(inflater, parent, false)
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
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = event.timedate

        val currentCalendar = Calendar.getInstance()
        val showStartTime = type == EventListFragment.PAST_EVENTS || calendar.after(currentCalendar)
        val startTime : String? = if(showStartTime){
            //Set date time
            val dateString = dateFormat.format(date)
            val timeString = timeFormat.format(date)
            "$dateString, $timeString"
        } else null

        val period: String? = if(event.period > 0) timeUntilTriggerString(event.period, event.periodUnit, event.timedate) else null

        holder.setOnClick(mContext, event.id)
        holder.bind(event, startTime, pet, period)
    }

    private fun timeUntilTrigger(period: Int, unit: Int, startTime: Long) : Long{
        val current = Calendar.getInstance()
        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = startTime

        val periodReal = DateUtils.getPeriod(unit, period)
        val calendarUnit = DateUtils.getCalendarUnit(unit)
        while(newCalendar.before(current)){
            newCalendar.add(calendarUnit, periodReal)
        }

        return newCalendar.timeInMillis - current.timeInMillis
    }

    private fun timeUntilTriggerString(period: Int, unit: Int, startTime: Long) : String {
        val trigger = timeUntilTrigger(period,unit,startTime)
        val newCalendar = Calendar.getInstance()
        newCalendar.timeInMillis = trigger

        val years = newCalendar.get(Calendar.YEAR) - 1970
        val days = newCalendar.get(Calendar.DAY_OF_YEAR) - 1
        val hours = newCalendar.get(Calendar.HOUR_OF_DAY)
        val minutes = newCalendar.get(Calendar.MINUTE)

        val strBuilder = StringBuilder()
        if(years > 0) {
            strBuilder.append("$years year")
            if(years > 1) strBuilder.append("s")
        }

        if(days > 0){
            if(!strBuilder.isBlank()) strBuilder.append(", ")
            strBuilder.append("$days day")
            if(days > 1) strBuilder.append("s")
        }

        if(hours > 0){
            if(!strBuilder.isBlank()) strBuilder.append(", ")
            strBuilder.append("$hours hour")
            if(hours > 1) strBuilder.append("s")
        }

        if(!strBuilder.isBlank()) strBuilder.append(", ")
        strBuilder.append("$minutes minute")
        if(minutes > 1) strBuilder.append("s")
        return strBuilder.toString()
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
