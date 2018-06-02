package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationListActivity
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
            int.putExtra("pet", item)
            context.startActivity(int)
        }

        binder.executePendingBindings()
    }
}

/*
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R

import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.view.ExtendedRelativeLayout

class PetHolder:  RecyclerView.ViewHolder {

    private val binder : ItemPetBinding

    constructor(bind: ItemPetBinding) : super(bind.root) {
        this.binder = bind
    }

    fun bind(pet: Pet){
        binder.pet = pet
        binder.executePendingBindings();
    }

    val name: TextView
    val layout: ExtendedRelativeLayout
    val birthDateLabel: TextView
    val birthDate: TextView
    val raceLabel: TextView
    val race: TextView
    val speciesLabel: TextView
    val species: TextView
    val chipLabel: TextView
    val chip: TextView
    val change: Button
    val delete: Button

    constructor(itemView: View) : super(itemView) {
        name = itemView.findViewById(R.id.name)
        layout = itemView.findViewById(R.id.pet_details)
        birthDateLabel = layout.findViewById(R.id.birth_label)
        birthDate = layout.findViewById(R.id.pet_birth_date)
        raceLabel = layout.findViewById(R.id.race_label)
        race = layout.findViewById(R.id.race)
        speciesLabel = layout.findViewById(R.id.species_label)
        species = layout.findViewById(R.id.species)
        chipLabel = layout.findViewById(R.id.chip_label)
        chip = layout.findViewById(R.id.chip_number)
        change = layout.findViewById(R.id.change_button)
        delete = layout.findViewById(R.id.delete_button)
    }
    */
