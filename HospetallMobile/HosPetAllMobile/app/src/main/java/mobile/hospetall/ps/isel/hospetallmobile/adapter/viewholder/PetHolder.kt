package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.view.ExtendedRelativeLayout

class PetHolder:  RecyclerView.ViewHolder {

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
        name = itemView.findViewById<TextView>(R.id.name)
        layout = itemView.findViewById<ExtendedRelativeLayout>(R.id.pet_details)
        birthDateLabel = layout.findViewById<TextView>(R.id.birth_label)
        birthDate = layout.findViewById<TextView>(R.id.pet_birth_date)
        raceLabel = layout.findViewById<TextView>(R.id.race_label)
        race = layout.findViewById<TextView>(R.id.race)
        speciesLabel = layout.findViewById<TextView>(R.id.species_label)
        species = layout.findViewById<TextView>(R.id.species)
        chipLabel = layout.findViewById<TextView>(R.id.chip_label)
        chip = layout.findViewById<TextView>(R.id.chip_number)
        change = layout.findViewById<Button>(R.id.change_button)
        delete = layout.findViewById<Button>(R.id.delete_button)
    }
}
