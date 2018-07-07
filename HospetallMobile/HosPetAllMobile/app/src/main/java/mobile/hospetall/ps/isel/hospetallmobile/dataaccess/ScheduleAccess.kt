package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.os.AsyncTask
import com.android.volley.Response
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

        private class GetAsyncTask(
                private val dao: EventDao,
                private val onSuccess: Response.Listener<Event>
        ) : AsyncTask<Int, Unit, Event>() {

            override fun doInBackground(vararg params: Int?): Event? {
                params[0]?.apply { return dao.get(this)  }
                return null
            }

            override fun onPostExecute(result: Event?) {
                super.onPostExecute(result)
                onSuccess.onResponse(result)
            }
        }

        private class GetListAsyncTask(
                private val dao: EventDao,
                private val supplier: (EventDao, Long) -> List<Event>,
                private val onSuccess: Response.Listener<List<Event>>
        ) : AsyncTask<Long, Unit, List<Event>>() {

            override fun doInBackground(vararg params: Long?): List<Event>? {
                params[0]?.apply {
                    return supplier(dao, this)
                }
                return null
            }

            override fun onPostExecute(result: List<Event>?) {
                super.onPostExecute(result)
                onSuccess.onResponse(result)
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

    fun get(id : Int, onSuccess: Response.Listener<Event>) {
        GetAsyncTask(eventDao, onSuccess)
    }

    fun getBefore(date: Long, onSuccess: Response.Listener<List<Event>>) {
        GetListAsyncTask(eventDao, { dao, time -> dao.getBefore(time) }, onSuccess)
    }

    fun getAfter(date: Long, onSuccess: Response.Listener<List<Event>>) {
        GetListAsyncTask(eventDao, { dao, time -> dao.getAfter(time) }, onSuccess)
    }

    fun getAll(onSuccess: Response.Listener<List<Event>>) {
        GetListAsyncTask(eventDao, { dao, _ -> dao.getAll() }, onSuccess)
    }

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