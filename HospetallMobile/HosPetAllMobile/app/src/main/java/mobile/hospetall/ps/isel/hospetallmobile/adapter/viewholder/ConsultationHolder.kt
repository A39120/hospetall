package mobilpetNamee.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

/*
class ConsultationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val procedure : ProcedureHolder
    val weight : LabelValueHolder
    val heartRhythm : LabelValueHolder
    val temperature : LabelValueHolder
    val veterinarian : LabelValueHolder

    fun parseToConsultation(res: Resources, consultation: Consultation, pet: Pet, vet: String) {
        weight.setLabelValue(res.getString(R.string.weight), consultation.weight?.toString())
        heartRhythm.setLabelValue(res.getString(R.string.heartRhythm), consultation.heartRhythm?.toString())
        temperature.setLabelValue(res.getString(R.string.temperature), consultation.temperature?.toString())
        veterinarian.setLabelValue(res.getString(R.string.veterinarian), vet)

        procedure.parseProcedure(res, consultation, pet)
        procedure.title.text = "Consultation"
    }

    init {
        procedure = ProcedureHolder(itemView.findViewById(R.id.procedure))
        val detail = itemView.findViewById<RelativeLayout>(R.id.consultation)
        weight = LabelValueHolder(detail.findViewById(R.id.weight))
        temperature = LabelValueHolder(detail.findViewById(R.id.temperature))
        heartRhythm= LabelValueHolder(detail.findViewById(R.id.heart_rhythm))
        veterinarian = LabelValueHolder(detail.findViewById(R.id.veterinarian))
    }

}
           */