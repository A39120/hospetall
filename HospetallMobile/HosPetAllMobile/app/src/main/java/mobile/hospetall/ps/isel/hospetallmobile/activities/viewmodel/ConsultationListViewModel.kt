package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class ConsultationListViewModel : ViewModel() {

    private val consultationRepo by lazy { ConsultationAccess() }
    private val petRepo by lazy { PetAccess() }
    private val allPets by lazy { petRepo.getAll() }
    private var pets : LiveData<List<Pet>>? = null
    private var consultationList : LiveData<List<Consultation>>? = null

    fun init(uri: String) {
        if(consultationList == null) {
            consultationList = consultationRepo.getList(uri)

            pets = Transformations.switchMap(consultationList!!, {
                val list = it
                        .map { it.petUri }
                        .distinct()

                Transformations.map(allPets, {
                    val pets = it
                    list.map {
                        val id = it
                        pets.find { it.uri == id }!!
                    }
                })
            })
        }
    }

    fun getConsultationList() = consultationList

    fun getPetList() = pets

}