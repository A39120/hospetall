package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class EventHolder(private val mBinder : ItemEventBinding) : AbstractHolder<ItemEventBinding>(mBinder) {

    fun bind(event: Event, pet: Pet? = null){
        mBinder.event = event

        if(pet!= null) {
            mBinder.setPet(pet)
        }

        mBinder.executePendingBindings()
    }
}