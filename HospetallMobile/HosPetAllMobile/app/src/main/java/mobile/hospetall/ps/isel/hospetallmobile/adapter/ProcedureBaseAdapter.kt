package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.TextView
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

abstract class ProcedureBaseAdapter<T : Procedure>(baseContext : Context,
                              procedure: Array<T>,
                              private val fromPet : Boolean,
                              private val petAccess: PetAccess?,
                              private val operation: String?)
    : BaseAdapter<T>(baseContext, R.layout.standard_item, procedure) {

    constructor(baseContext : Context,
                procedure: Array<T>,
                petAccess: PetAccess,
                operation: String?) : this(baseContext, procedure, false, petAccess, operation)

    constructor(baseContext: Context, procedure: Array<T>, operation: String?) : this(baseContext, procedure, false, null, operation)


    override fun saveToHolder(rowView: View) {
        rowView.tag = getItemHolder(rowView)
    }

    override fun manipulateItem(rowView: View, obj: T): View {
        val holder = rowView.tag as ItemHolder

        holder.apply {
            date.text = obj.date.toString()
            if(!fromPet && obj.petUri != null) {
                val petUri = Uri.parse(obj.petUri)
                petAccess?.get(
                        petUri,
                        Response.Listener{
                            holder.pet.text = it.name
                        },
                        Response.ErrorListener {  }
                )
            }
            procedure.text = operation
        }
        return rowView
    }


    data class ItemHolder(
            val date: TextView,
            val pet: TextView,
            val procedure: TextView
    )

    private fun getItemHolder(view: View) =
            ItemHolder(
                    view.findViewById(R.id.date),
                    view.findViewById(R.id.pet),
                    view.findViewById(R.id.procedure)
            )
}
