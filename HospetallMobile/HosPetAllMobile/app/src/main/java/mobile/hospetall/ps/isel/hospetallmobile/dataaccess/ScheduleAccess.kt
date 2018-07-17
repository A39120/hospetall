package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.EventDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Event

class ScheduleAccess {
    companion object {
        private val mInstance by lazy { ScheduleAccess() }
        fun getInstance() = mInstance

        private class InsertAsyncTask(
                private val dao : EventDao,
                private val resultHandler : (Int) -> Unit
        ) : AsyncTask<Event, Unit, Int>(){
            override fun doInBackground(vararg params: Event?) =
                params[0]?.let { dao.insertOrUpdate(it).toInt() }

            override fun onPostExecute(result: Int) {
                super.onPostExecute(result)
                resultHandler(result)
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

    fun put(event: Event, response :(Int) -> Unit) {
        InsertAsyncTask(eventDao, response).execute(event)
    }

    fun delete(id: Int) {
        DeleteAsyncTask(eventDao).execute(id)
    }

    fun appoint(event: Event){
        //TODO: Appoint consultation/treatment
    }

}