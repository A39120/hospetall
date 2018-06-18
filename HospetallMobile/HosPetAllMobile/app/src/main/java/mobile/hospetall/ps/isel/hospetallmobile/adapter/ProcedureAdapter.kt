package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureItemHolder
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ItemProcedureBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

open class ProcedureAdapter<T : Procedure> (
        mContext: Context,
        private val list: Array<T>)
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

    /**
     * TODO: Is this even used?
     */
    override fun onBindViewHolder(itemHolder: ProcedureItemHolder, position: Int) {
        val procedure = list[position]
        itemHolder.bind(procedure)
    }
}