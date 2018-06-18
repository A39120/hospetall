package mobile.hospetall.ps.isel.hospetallmobile.utils

import android.os.AsyncTask

class AsyncTaskImpl<T>(
        private val uri: String,
        private val supplier: (String) -> T,
        private val listener: (T?) -> Unit
) : AsyncTask<String, Unit, T>() {

    override fun doInBackground(vararg params: String?) = supplier(uri)

    override fun onPostExecute(result: T?) { listener(result) }

}