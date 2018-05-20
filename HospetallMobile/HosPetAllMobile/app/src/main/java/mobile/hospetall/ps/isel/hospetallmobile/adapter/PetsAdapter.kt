package mobile.hospetall.ps.isel.hospetallmobile.adapter

import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import mobile.hospetall.ps.isel.hospetallmobile.view.ExtendedRelativeLayout

class PetsAdapter(
                    baseContext: Context,
                    private val pets: Array<Pet>) :
        ArrayAdapter<Pet>(baseContext, R.layout.pet_item, pets) {


    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = view?: inflater.inflate(R.layout.pet_item, parent, false)

        if(view == null) {
            val nameView = rowView.findViewById<TextView>(R.id.name)
            val expand = rowView.findViewById<Button>(R.id.expand)
            val detail = rowView.findViewById<ExtendedRelativeLayout>(R.id.pet_details)
            val birthDateLabel = detail .findViewById<TextView>(R.id.birth_label)
            val birthDateView = detail .findViewById<TextView>(R.id.pet_birth_date)
            val raceLabel = detail .findViewById<TextView>(R.id.race_label)
            val raceView = detail .findViewById<TextView>(R.id.race)
            val speciesLabel = detail .findViewById<TextView>(R.id.species_label)
            val speciesView = detail .findViewById<TextView>(R.id.species)
            val chipLabel = detail .findViewById<TextView>(R.id.chip_label)
            val chipView = detail .findViewById<TextView>(R.id.chip_number)
            val consultationButton = detail .findViewById<Button>(R.id.consultation_button)
            val treatmentButton = detail .findViewById<Button>(R.id.treatment_button)

            val listHolder = PetListHolder(nameView, expand, detail)
            val detailHolder = PetDetailHolder(birthDateLabel, birthDateView, raceLabel, raceView, speciesLabel, speciesView, chipLabel, chipView, consultationButton, treatmentButton)
            rowView.tag = PetHolder(listHolder, detailHolder, pets[position])
        }

        val holder = rowView.tag as PetHolder
        //adaptPetDetailLayout(pets[position], holder)

        holder.list.apply {
            name.text = pets[position].name
            expand.setOnClickListener {
                when(holder.list.detail.visibility){
                    View.GONE -> { onExpand(holder, pets[position]) }
                    View.VISIBLE -> { onMinimize(holder) }
                }
            }
        }

        return rowView
    }

    data class PetListHolder(val name: TextView,
                             val expand: Button,
                             val detail: ExtendedRelativeLayout
    )

    data class PetDetailHolder(
            val birthDateLabel: TextView,
            val birthDate: TextView,
            val raceLabel: TextView,
            val race: TextView,
            val speciesLabel: TextView,
            val species: TextView,
            val chipLabel: TextView,
            val chip: TextView,
            val consultation: Button,
            val treatment: Button
    )

    data class PetHolder(
            val list: PetListHolder,
            val detail: PetDetailHolder,
            val pet: Pet
    )


    private fun onExpand(holder: PetHolder, pet: Pet){
        Log.i("ACCESS/PET_LIST", "Showing pet detail.")
        holder.list.detail.afterVisible = {  adaptPetDetailLayout(pet, holder) }
        holder.list.detail.visibility = View.VISIBLE
        holder.list.expand.text="Close"//"\uf07c"
    }

    private fun onMinimize(holder: PetHolder) {
        Log.i("ACCESS/PET_LIST", "Minimizing pet detail.")
        holder.list.detail.visibility = View.GONE
        holder.list.expand.text="\uf07b"
    }

    private fun adaptPetDetailLayout(pet : Pet, holder:PetHolder) = holder.detail.apply {
        if(pet.birthdate == null) {
            birthDateLabel.visibility = View.GONE
            birthDate.visibility = View.GONE
        } else
            birthDate.text = pet.birthdate

        if(pet.race == null){
            raceLabel.visibility = View.GONE
            race.visibility = View.GONE
        } else race.text = pet.birthdate

        if(pet.species == null){
            speciesLabel.visibility = View.GONE
            species.visibility = View.GONE
        } else species.text = pet.birthdate

        Log.i("ACCESS/PET_LIST", "Chip number displayed: ${pet.chipNumber}")
        chip.text = pet.chipNumber.toString()

        consultation.visibility = if(pet.consultationUri == null) View.GONE else  View.VISIBLE
        treatment.visibility =  if(pet.treatmentUri == null) View.GONE else View.VISIBLE

    }

}
