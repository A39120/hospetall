package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

/*
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

class ProcedureHolder(itemRow: View) : RecyclerView.ViewHolder(itemRow) {

    val title : TextView
    val date : TextView
    val pet: LabelValueHolder
    val caseHistory : LabelValueHolder
    val diagnosis : LabelValueHolder
    val treatment : LabelValueHolder
    val observations : LabelValueHolder

    fun parseProcedure(res: Resources, procedure : Procedure, petValue: Pet) {
        date.text = procedure.date
        pet.setLabelValue("Pet", petValue.name)

        caseHistory.setLabelValue(res.getString(R.string.caseHistory), procedure.caseHistory)
        diagnosis.setLabelValue(res.getString(R.string.diagnosis), procedure.diagnosis)
        treatment.setLabelValue(res.getString(R.string.treatment), procedure.treatment)
        observations.setLabelValue(res.getString(R.string.observations), procedure.observations)
    }

    init {
        title = itemRow.findViewById(R.id.title)
        date = itemRow.findViewById(R.id.date)
        pet = LabelValueHolder(itemRow.findViewById<RelativeLayout>(R.id.pet))
        caseHistory = LabelValueHolder(itemRow.findViewById<RelativeLayout>(R.id.case_history))
        diagnosis = LabelValueHolder(itemRow.findViewById<RelativeLayout>(R.id.diagnosis))
        treatment = LabelValueHolder(itemRow.findViewById<RelativeLayout>(R.id.treatment))
        observations = LabelValueHolder(itemRow.findViewById<RelativeLayout>(R.id.observations))
    }
}

*/