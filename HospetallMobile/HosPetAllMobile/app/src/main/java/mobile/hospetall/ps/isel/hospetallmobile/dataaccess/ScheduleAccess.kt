package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class ScheduleAccess {

    private val database = MobileDatabase.getInstance()
    private val eventDao = database.eventDao()

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