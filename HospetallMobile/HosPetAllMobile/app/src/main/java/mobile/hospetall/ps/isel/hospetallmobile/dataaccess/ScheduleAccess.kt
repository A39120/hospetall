package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class ScheduleAccess(application: HospetallApplication) {

    private val eventDao = application.database.eventDao()

    fun get(id : Int) = eventDao.get(id)

    fun getBefore(date: Long) = eventDao.getBefore(date)

    fun getAfter(date: Long) =
            eventDao.getAfter(date)

    fun getAll() =
             eventDao.getAll()

    fun put(event: Event) {
        eventDao.insertOrUpdate(event)
    }

    fun delete(id: Int) {
        eventDao.deleteById(id)
    }

    fun appoint(event: Event){
        //TODO: Appoint consultation/treatment
    }

}