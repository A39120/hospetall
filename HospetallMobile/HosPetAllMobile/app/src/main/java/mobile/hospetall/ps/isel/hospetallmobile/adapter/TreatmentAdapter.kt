package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentAdapter(baseContext : Context,
                       treatments: Array<Treatment>,
                       fromPet : Boolean = true,
                       petAccess: PetAccess? = null)
    : ProcedureBaseAdapter<Treatment>(baseContext, treatments, fromPet, petAccess, "treatment")

