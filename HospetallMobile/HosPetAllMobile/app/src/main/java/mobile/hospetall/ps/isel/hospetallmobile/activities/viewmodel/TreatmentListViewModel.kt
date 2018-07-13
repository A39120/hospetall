package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

class TreatmentListViewModel : ViewModel() {

    private val treatmentRepo by lazy{ TreatmentAccess() }
    private var treatmentList : LiveData<List<Treatment>>? = null
    private val petRepo by lazy { PetAccess() }
    private val allPets by lazy { petRepo.getAll() }
    private var petList : LiveData<List<Pet>>? = null

    fun init(uri : String) {
        if(treatmentList == null) {
            treatmentList = treatmentRepo.getList(uri)
            petList = Transformations.switchMap(treatmentList!!, {
                val treatments = it.map { it.petUri }
                Transformations.map(allPets, {
                    val pets = it
                    treatments.map {
                        val id = it
                        pets.find{ it.uri == id }!!
                    }
                })
            })
        }
    }

    fun getTreatmentList() = treatmentList

    fun getPetList() = petList

}