package mobile.hospetall.ps.isel.hospetallmobile.layout

import android.net.Uri
import android.view.View
import android.widget.TextView
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

data class ConsultationItemHolder(
        val date: TextView,
        val pet: TextView,
        val procedure: TextView
)

fun getConsultationItemHolder(view: View) =
        ConsultationItemHolder(
                view.findViewById(R.id.date),
                view.findViewById(R.id.pet),
                view.findViewById(R.id.procedure)
        )

fun adaptConsultationLayout(consultation: Consultation,
                            holder : ConsultationItemHolder,
                            petAccess: PetAccess){
            holder.date.text = consultation.date.toString()
            if(consultation.petUri != null)
                petAccess.get(
                    Uri.parse(consultation.petUri),
                    Response.Listener {
                        holder.pet.text = it.name
                    },
                    Response.ErrorListener { }
                )
}

