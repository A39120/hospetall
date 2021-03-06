package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class PetViewModel(application: HospetallApplication) : AndroidViewModel(application) {

    private val petRepo by lazy { PetAccess(application) }

    private var pet: LiveData<Pet>? = null

    private lateinit var uri : String

    fun init(uri: String) {
        if(pet==null){
            this.uri = uri
            pet = petRepo.get(uri)
        }
    }

    fun getPet() = pet

    fun update(){
        petRepo.updateCollectionFromNetwork(uri)
    }

}