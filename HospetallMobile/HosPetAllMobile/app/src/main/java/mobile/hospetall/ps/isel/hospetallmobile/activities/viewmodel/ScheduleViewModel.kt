package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment.Companion.FUTURE_EVENTS
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment.Companion.PAST_EVENTS
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment.Companion.PERIODIC_EVENTS
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import java.util.*

/**
 * [ViewModel] for [Event] present in the [ScheduleActivity]
 */
class ScheduleViewModel : ViewModel() {
    private val scheduleRepo by lazy { ScheduleAccess.getInstance() }
    private val petRepo by lazy { PetAccess.getInstance() }
    private var events : LiveData<List<Event>>? = null
    private var allPets : LiveData<List<Pet>>? = null
    private var pets : LiveData<List<Pet>>? = null

    fun init(type: Int) {
        events = when(type){
            PERIODIC_EVENTS -> scheduleRepo.getPeriodic()
            FUTURE_EVENTS -> scheduleRepo.getAfter(Calendar.getInstance().timeInMillis)
            PAST_EVENTS -> scheduleRepo.getBefore(Calendar.getInstance().timeInMillis)
            else -> scheduleRepo.getAll()
        }

        val allPetsUri = UriUtils.getClientsPetsUri(getId()).build().toString()
        allPets = petRepo.getList(allPetsUri)

        pets = Transformations.switchMap(events!!, {
            val list = it
                    .map { it.pet }
                    .distinct()
                    .filterNotNull()

            Transformations.map(allPets!!, {
                val pets = it
                list.map {
                    val id = it
                    pets.find{ it.id == id }
                }.filterNotNull()
            })
        })
    }

    fun getEvents() = events

    fun getPets() = pets

}