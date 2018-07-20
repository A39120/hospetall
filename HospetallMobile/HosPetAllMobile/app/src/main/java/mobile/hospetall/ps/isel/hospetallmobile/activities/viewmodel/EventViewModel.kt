package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class EventViewModel : ViewModel() {

    private val eventRepo by lazy { ScheduleAccess() }
    private val petRepo by lazy { PetAccess() }
    private var event: LiveData<Event>? = null
    private var allPets : LiveData<List<Pet>>? = null
    private var pet: LiveData<Pet>? = null

    fun init(id: Int? = null){
        if(event == null) {
            if(id!= null){
                event = eventRepo.get(id)
                pet = Transformations.switchMap(event!!, {
                    it?.pet?.let{
                        val uri = UriUtils.getPetUri(it).build().toString()
                        petRepo.get(uri)
                    }
                })
            }
            }

            val uri = UriUtils.getClientsPetsUri(getId()).build().toString()
            allPets = petRepo.getList(uri)
    }

    fun getEvent() = event
    fun getPet() = pet
    fun getAllPets() = allPets
}