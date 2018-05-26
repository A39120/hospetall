package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

class ConsultationAdapter(baseContext : Context,
                          consultations: Array<Consultation>,
                          fromPet : Boolean = true,
                          petAccess: PetAccess? = null)
    : ProcedureBaseAdapter<Consultation>(baseContext, consultations, fromPet, petAccess, "consultation")

