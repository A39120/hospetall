package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import android.util.LruCache
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.utils.AsyncTaskImpl

class ScheduleAccess(
        private val cache: LruCache<Int, Event?>
) {

    private val eventDao = MobileDatabase.getInstance().EventDao()

    fun get(id : Int, listener: (Event?) -> Unit) {
        AsyncTaskImpl(
               id,
                {cache.get(id) ?: eventDao.get(id)},
                listener
        )
    }

    fun getBefore(date: Long, listener: (List<Event>?) -> Unit) {
        AsyncTaskImpl(
                date,
                {eventDao.getBefore(it)},
                listener
        )
    }

    fun getAfter(date: Long, listener: (List<Event>?) -> Unit) {
        AsyncTaskImpl(
                date,
                {eventDao.getAfter(it)},
                listener
        )
    }


    fun getAll(listener: (List<Event>?) -> Unit) {
        AsyncTaskImpl(
                -1,
                {eventDao.getAll()},
                listener
        )
    }

    fun put(event: Event) {
        AsyncTask.execute {
            eventDao.insertOrUpdate(event)
            cache.put(event.id, event)
        }
    }

    fun delete(id: Int) {
        AsyncTask.execute {
            eventDao.deleteById(id)
            cache.remove(id)
        }
    }

    fun appoint(event: Event){
        //TODO: Appoint consultation/treatment
    }

}