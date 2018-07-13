package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.PetHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetsAdapter(
        private val mContext: Context,
        private var petArray: List<Pet>)
    : RecyclerView.Adapter<PetHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/PET"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHolder {
        val petBinder = ItemPetBinding.inflate(inflater, parent, false)
        return PetHolder(petBinder)
    }

    override fun onBindViewHolder(holder: PetHolder, position: Int) {
        val pet = petArray[position]
        holder.bind(pet, mContext)
    }

    fun setPetList(list: List<Pet>){
        this.petArray = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = petArray.size
}