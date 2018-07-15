package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.PetHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetsAdapter(
        private val mContext: Context,
        private val pets: MutableList<Pet> = mutableListOf())
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
        val pet = pets[position]
        holder.bind(pet, mContext)
    }

    fun setPetList(list: List<Pet>){
        Log.i(TAG, "Altering procedure pets.")
        val res = DiffUtil.calculateDiff(BaseDiffUtilCallback(list, pets))
        pets.clear()
        pets.addAll(list)
        res.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = pets.size
}