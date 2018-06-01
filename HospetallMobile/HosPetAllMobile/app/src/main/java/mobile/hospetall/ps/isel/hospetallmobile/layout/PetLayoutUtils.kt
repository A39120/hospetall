package mobile.hospetall.ps.isel.hospetallmobile.layout

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
/*
data class PetDetailHolder(
        val birthDateLabel: TextView,
        val birthDate: TextView,
        val raceLabel: TextView,
        val race: TextView,
        val speciesLabel: TextView,
        val species: TextView,
        val chipLabel: TextView,
        val chip: TextView
        //val consultation: Button,
        //val treatment: Button
)

fun getPetDetailHolder(detail: RelativeLayout) : PetDetailHolder {
    val birthDateLabel = detail .findViewById<TextView>(R.id.birth_label)
    val birthDateView = detail .findViewById<TextView>(R.id.pet_birth_date)
    val raceLabel = detail .findViewById<TextView>(R.id.race_label)
    val raceView = detail .findViewById<TextView>(R.id.race)
    val speciesLabel = detail .findViewById<TextView>(R.id.species_label)
    val speciesView = detail .findViewById<TextView>(R.id.species)
    val chipLabel = detail .findViewById<TextView>(R.id.chip_label)
    val chipView = detail .findViewById<TextView>(R.id.chip_number)
    //val consultationButton = detail .findViewById<Button>(R.id.consultation_button)
    //val treatmentButton = detail .findViewById<Button>(R.id.treatment_button)
    return PetDetailHolder(birthDateLabel, birthDateView, raceLabel, raceView, speciesLabel, speciesView, chipLabel, chipView)//, consultationButton, treatmentButton)
}

fun adaptPetDetailLayout(pet : Pet, holder:PetDetailHolder) = holder.apply {
    if(pet.birthDate == null) {
        birthDateLabel.visibility = View.GONE
        birthDate.visibility = View.GONE
    } else
        birthDate.text = pet.birthDate

    if(pet.race == null){
        raceLabel.visibility = View.GONE
        race.visibility = View.GONE
    } else race.text = pet.race

    if(pet.species == null){
        speciesLabel.visibility = View.GONE
        species.visibility = View.GONE
    } else species.text = pet.species

    Log.i("ACCESS/PET_LIST", "Chip number displayed: ${pet.chipNumber}")
    chip.text = pet.chipNumber.toString()

    //consultation.visibility = if(pet.consultationUri == null) View.GONE else  View.VISIBLE
    //treatment.visibility =  if(pet.treatmentUri == null) View.GONE else View.VISIBLE

}
*/