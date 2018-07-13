package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.EventDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class ScheduleAccess {
    companion object {

        private class InsertAsyncTask(
                private val dao : EventDao
        ) : AsyncTask<Event, Unit, Unit>(){
            override fun doInBackground(vararg params: Event?) {
                params[0]?.apply { dao.insertOrUpdate(this) }
            }
        }

        private class DeleteAsyncTask( private val dao : EventDao )
            :AsyncTask<Int, Unit, Unit>() {

            override fun doInBackground(vararg params: Int?) {
                params[0]?.apply{ dao.deleteById(this) }
            }
        }
    }

    private val database = MobileDatabase.getInstance()
    private val eventDao = database.eventDao()

    fun get(id : Int) = eventDao.get(id)

    fun getBefore(date: Long) =
            eventDao.getBefore(date)

    fun getAfter(date: Long) =
            eventDao.getAfter(date)

    fun getAll() = eventDao.getAll()

    fun put(event: Event) {
        InsertAsyncTask(eventDao).execute(event)
    }

    fun delete(id: Int) {
        DeleteAsyncTask(eventDao).execute(id)
    }

    fun appoint(event: Event){
        //TODO: Appoint consultation/treatment
    }

}