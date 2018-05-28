package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder.ProcedureHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

class ProcedureAdapter<T : Procedure> (
        mContext: Context,
        private val list: List<T>)
    : RecyclerView.Adapter<ProcedureHolder>() {
    companion object {
        private const val TAG = "HPA/ADAPTER/PROCEDURE"
    }

    private val inflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureHolder {
        Log.i(TAG, "Creating view holder for consultation list.")
        val view =  inflater.inflate(R.layout.standard_item, parent, false)
        return ProcedureHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ProcedureHolder, position: Int) {
        val procedure = list.get(position)
        holder.apply {
            date.text = procedure.date
        }
    }

}