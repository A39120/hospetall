package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment.Companion.FUTURE_EVENTS
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment.Companion.PAST_EVENTS
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import java.util.*

/**
 * [ViewModel] for [Event] present in the [ScheduleActivity]
 */
class ScheduleViewModel : ViewModel() {
    private val scheduleRepo by lazy { ScheduleAccess() }
    private val petRepo by lazy { PetAccess() }
    private var events : LiveData<List<Event>>? = null
    private val allPets by lazy { petRepo.getAll() }
    private var pets : LiveData<List<Pet>>? = null

    fun init(type: Int) {
        events = when(type){
            FUTURE_EVENTS -> scheduleRepo.getAfter(Calendar.getInstance().timeInMillis)
            PAST_EVENTS -> scheduleRepo.getBefore(Calendar.getInstance().timeInMillis)
            else -> scheduleRepo.getAll()
        }

        pets = Transformations.switchMap(events!!, {
            val list = it
                    .map { it.pet }
                    .distinct()
                    .filter { it != null }

            Transformations.map(allPets, {
                val pets = it
                list.map {
                    val id = it
                    pets.find{ it.id == id }!!
                }
            })
        })
    }

    fun getEvents() = events

    fun getPets() = pets

}