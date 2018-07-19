package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.activities.EventActivity
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemNonPeriodicEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class EventHolder(private val mBinder : ItemNonPeriodicEventBinding) : AbstractHolder<ItemNonPeriodicEventBinding>(mBinder) {

    fun bind(event: Event, dateTime : String?, pet: Pet? = null, trigger : String?){
        mBinder.event = event
        mBinder.dateAndTime = dateTime
        pet?.apply { mBinder.pet = this }
        mBinder.repeatsIn = trigger
        mBinder.executePendingBindings()
    }

    fun setOnClick(context: Context, id: Int){
        mBinder.eventItem.setOnClickListener {
            EventActivity.start(context, id)
        }
    }
}