package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemProcedureBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

class ProcedureAdapter<T : Procedure> (
        mContext: Context,
        private val list: List<T>)
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

    override fun getItemCount() = list.size

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list.get(position)
        itemHolder.bind(procedure)
    }


    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureItemHolder {
        Log.i(TAG, "Creating view holder for consultation list.")
        val view =  inflater.inflate(R.layout.item_procedure, parent, false)
        return ProcedureItemHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list.get(position)
        itemHolder.apply {
            date.text = procedure.date
        }
    }
*/
}