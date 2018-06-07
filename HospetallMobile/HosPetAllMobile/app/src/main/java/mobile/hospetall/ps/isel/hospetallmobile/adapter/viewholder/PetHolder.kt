package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemPetBinding

class PetHolder(private val binder: ItemPetBinding) : AbstractHolder<ItemPetBinding>(binder) {
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
            val int = Intent(context, PetActivity::class.java)
            //int.putExtra(context.resources.getString(R.string.pet), item)
            int.putExtra("id", item.id)
            context.startActivity(int)
        }
        binder.executePendingBindings()
    }
}