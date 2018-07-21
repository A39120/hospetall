package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ConsultationListViewModel : ViewModel() {

    private val consultationRepo by lazy { ConsultationAccess.getInstance() }
    private val petRepo by lazy { PetAccess.getInstance() }
    private var allPets  : LiveData<List<Pet>>? = null
    private var pets : LiveData<List<Pet>>? = null
    private var consultationList : LiveData<List<Consultation>>? = null
    private lateinit var uri : String

    fun init(uri: String) {
        if(consultationList == null) {
            this.uri = uri
            consultationList = consultationRepo.getList(uri)

            val allPetsUri = UriUtils.getClientsPetsUri(getId()).build().toString()
            allPets = petRepo.getList(allPetsUri)
            pets = Transformations.switchMap(consultationList!!, {
                val list = it.map { it.petUri }

                Transformations.map(allPets!!, {
                    val pets = it
                    list.mapNotNull {
                        val currentUri = it
                        pets.find { currentUri == it.uri }
                    }
                })
            })
        }
    }

    fun update(){
        consultationRepo.updateCollectionFromNetwork(uri)
    }

    fun getConsultationList() = consultationList

    fun getPetList() = pets

}