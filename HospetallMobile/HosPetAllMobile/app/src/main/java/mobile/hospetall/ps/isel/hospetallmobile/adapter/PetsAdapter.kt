package mobile.hospetall.ps.isel.hospetallmobile.adapter

import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import mobile.hospetall.ps.isel.hospetallmobile.activities.ConsultationListActivity
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.layout.PetDetailHolder
import mobile.hospetall.ps.isel.hospetallmobile.layout.adaptPetDetailLayout
import mobile.hospetall.ps.isel.hospetallmobile.layout.getPetDetailHolder
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
            //val expand = rowView.findViewById<Button>(R.id.expand)
            val detail = rowView.findViewById<ExtendedRelativeLayout>(R.id.pet_details)

            val listHolder = PetListHolder(nameView, detail)
            val detailHolder = getPetDetailHolder(detail)

            rowView.tag = PetHolder(listHolder, detailHolder, pets[position])
        }

        val holder = rowView.tag as PetHolder
        //adaptPetDetailLayout(pets[position], holder)


        holder.list.name.text = pets[position].name
        holder.list.name.setOnClickListener {
            when(holder.list.detail.visibility){
                View.GONE -> { onExpand(holder, pets[position]) }
                View.VISIBLE -> { onMinimize(holder) }
            }
        }

        return rowView
    }

    data class PetListHolder(val name: TextView,
                             val detail: ExtendedRelativeLayout
    )

    data class PetHolder(
            val list: PetListHolder,
            val detail: PetDetailHolder,
            val pet: Pet
    )

    private fun onExpand(holder: PetHolder, pet: Pet){
        Log.i("ACCESS/PET_LIST", "Showing pet detail.")
        holder.list.detail.afterVisible = {  adaptPetDetailLayout(pet, holder.detail) }
        holder.list.detail.setOnClickListener {
            val int = Intent(this.context, PetActivity::class.java)
            int.putExtra("pet", pet)
            startActivity(this.context, int, null)
        }
        holder.list.detail.visibility = View.VISIBLE
    }

    private fun onMinimize(holder: PetHolder) {
        Log.i("ACCESS/PET_LIST", "Minimizing pet detail.")
        holder.list.detail.visibility = View.GONE
    }

    override fun getItemViewType(position: Int) = position
    override fun getItemId(position: Int) = position as Long
}
