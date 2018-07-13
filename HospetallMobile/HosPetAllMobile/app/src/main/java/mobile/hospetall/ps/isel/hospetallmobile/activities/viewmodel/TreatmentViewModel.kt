package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentViewModel : ViewModel() {

    private val treatmentRepo by lazy { TreatmentAccess() }
    private val petRepo by lazy { PetAccess() }

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