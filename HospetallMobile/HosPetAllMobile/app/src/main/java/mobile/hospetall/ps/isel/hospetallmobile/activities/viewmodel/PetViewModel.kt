package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetViewModel : ViewModel() {

    private val petRepo by lazy { PetAccess.getInstance() }
    private var pet: LiveData<Pet>? = null

    fun init(uri: String) {
        if(pet==null){
            pet = petRepo.get(uri)
        }
    }

    fun getPet() = pet
}