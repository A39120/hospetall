package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.PetHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetsAdapter(
        private val mContext: Context,
        private val petArray: List<Pet>)
    : RecyclerView.Adapter<PetHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/PET"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHolder {
        Log.i(TAG, "Creating view holder for pet list.")
        val view =  inflater.inflate(R.layout.pet_item, parent, false)
        return PetHolder(view)
    }

    override fun getItemCount() = petArray.size

    override fun onBindViewHolder(holder: PetHolder, position: Int) {
        val pet = petArray[position]
        holder.apply {
            name.text = pet.name
            name.setOnClickListener {
                when(layout.visibility){
                    View.GONE -> { layout.visibility = View.VISIBLE }
                    View.VISIBLE -> { layout.visibility = View.GONE }
                }
            }

            layout.setOnClickListener {
                val int = Intent(mContext, PetActivity::class.java)
                int.putExtra("pet", pet)
                mContext.startActivity(int)
            }

            if(pet.birthDate == null) {
                birthDateLabel.visibility = View.GONE
                birthDate.visibility = View.GONE
            } else birthDate.text = pet.birthDate

            if(pet.race == null){
                raceLabel.visibility = View.GONE
                race.visibility = View.GONE
            } else race.text = pet.race

            if(pet.species == null){
                speciesLabel.visibility = View.GONE
                species.visibility = View.GONE
            } else species.text = pet.species

            chip.text = pet.chipNumber.toString()
        }
    }

}