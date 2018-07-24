package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.content.Context
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetHolder(private val binder: ItemPetBinding) : AbstractHolder<ItemPetBinding>(binder) {

    /**
     * Binds information to adapted by the [PetAdapter]
     */
    fun bind(item: Pet, context: Context) {
        binder.pet = item
        binder.name.setOnClickListener {
            binder.petDetails.visibility = when(binder.petDetails.visibility) {
                View.GONE -> View.VISIBLE
                View.VISIBLE -> View.GONE
                else -> View.GONE
            }
        }

        binder.petDetails.setOnClickListener{
            PetActivity.start(context, item)
        }

        binder.executePendingBindings()
    }
}