package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemProcedureBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

abstract class ProcedureAdapter<T : Procedure> (
        private val mContext: Context,
        private val procedures: MutableList<T>,
        private val pets: MutableList<Pet>)
    : RecyclerView.Adapter<ProcedureItemHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/PROCEDURE"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureItemHolder {
        Log.i(TAG, "Creating view holder for consultation list.")
        val binder = ItemProcedureBinding.inflate(inflater, parent, false)
        return ProcedureItemHolder(binder)
    }

    fun setPets(newPets : List<Pet>){
        Log.i(TAG, "Altering procedure pets.")
        val res = DiffUtil.calculateDiff(BaseDiffUtilCallback(pets, newPets))
        pets.clear()
        pets.addAll(newPets)
        res.dispatchUpdatesTo(this)
    }

    fun setProcedure(newProcedures : List<T>){
        Log.i(TAG, "Altering procedures.")
        val res = DiffUtil.calculateDiff(BaseDiffUtilCallback(procedures, newProcedures))
        procedures.clear()
        procedures.addAll(newProcedures)
        res.dispatchUpdatesTo(this)
    }

    protected abstract fun goToActivity(context : Context,  id: Int)

    override fun getItemCount() = procedures.size

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = procedures[position]
        val pet = pets.find{ procedure.petUri == it.uri}
        itemHolder.bind(
                procedure,
                pet?.name,
                listener = View.OnClickListener{ goToActivity(mContext, procedure.id) })
    }
}