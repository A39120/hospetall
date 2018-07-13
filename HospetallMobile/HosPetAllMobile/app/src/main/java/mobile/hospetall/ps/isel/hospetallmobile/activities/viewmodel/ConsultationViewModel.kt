package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

class ConsultationViewModel : ViewModel() {

    private val consultationRepo by lazy { ConsultationAccess() }
    private val petRepo by lazy { PetAccess() }
    private var consultation : LiveData<Consultation>? = null
    private var pet : LiveData<Pet>? = null

    fun init(uri: String) {
        if(consultation == null) {
            consultation = consultationRepo.get(uri)
            pet = Transformations.switchMap(consultation!!, {
                petRepo.get(it.petUri)
            })
        }
    }

    fun getConsultation() = consultation
    fun getPet() = pet


}