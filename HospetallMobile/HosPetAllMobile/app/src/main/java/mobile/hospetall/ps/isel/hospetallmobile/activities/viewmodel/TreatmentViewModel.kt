package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentViewModel(application: HospetallApplication) : AndroidViewModel(application) {

    private val treatmentRepo by lazy { TreatmentAccess(application) }
    private val petRepo by lazy { PetAccess(application) }

    private var treatment : LiveData<Treatment>? = null
    private var pet : LiveData<Pet>? = null

    fun init(uri: String){
            if(treatment == null) {
                treatment = treatmentRepo.get(uri)
                pet = Transformations.switchMap(treatment!!, { petRepo.get(it.petUri) })
            }
    }

    fun getTreatment() = treatment
    fun getPet() = pet

}