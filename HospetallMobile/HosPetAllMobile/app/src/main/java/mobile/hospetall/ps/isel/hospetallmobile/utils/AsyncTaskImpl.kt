package mobile.hospetall.ps.isel.hospetallmobile.utils

import android.os.AsyncTask

class AsyncTaskImpl<T, U>(
        private val id: U,
        private val supplier: (U) -> T,
        private val listener: (T?) -> Unit
) : AsyncTask<Unit, Unit, T>() {

    override fun doInBackground(vararg params: Unit) = supplier(id)

    override fun onPostExecute(result: T?) { listener(result) }

}