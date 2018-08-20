package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class TreatmentListViewModel(application: HospetallApplication) : AndroidViewModel(application) {

    private val treatmentRepo by lazy{ TreatmentAccess(application) }
    private var treatmentList : LiveData<List<Treatment>>? = null
    private val petRepo by lazy { PetAccess(application) }
    private var allPets : LiveData<List<Pet>>? = null
    private var petList : LiveData<List<Pet>>? = null
    private lateinit var uri : String

    fun init(uri : String) {
        if(treatmentList == null) {
            this.uri = uri
            treatmentList = treatmentRepo.getList(uri)

            val allPetsUri = UriUtils.getClientsPetsUri(getId()).build().toString()
            allPets = petRepo.getList(allPetsUri)
            petList = Transformations.switchMap(treatmentList!!, {
                val treatments = it
                        .map { it.petUri }

                Transformations.map(allPets!!, {
                    val pets = it
                    treatments.mapNotNull {
                        val currentUri = it
                        pets.find{ it.uri == currentUri }
                    }
                })
            })
        }
    }

    fun update(){
        treatmentRepo.updateCollectionFromNetwork(uri)
    }

    fun getTreatmentList() = treatmentList
    fun getPetList() = petList

}