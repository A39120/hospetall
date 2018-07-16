package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class PetListViewModel : ViewModel() {
    private val petRepo by lazy { PetAccess.getInstance() }
    private var pet : LiveData<List<Pet>>? = null

    fun init(id: Int) {
        if(pet == null) {
            val allPet = UriUtils.getClientsPetsUri(id).toString()
            pet = petRepo.getList(allPet)
        }
    }

    fun getPetList() = pet



}