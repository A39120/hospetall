package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.getBaseUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import java.util.concurrent.CompletableFuture

class PetAccess(context: Context) {
    companion object {
        const val TAG = "PET/DATA_ACCESS"
    }
    private val app = context.applicationContext as HospetallApplication
    private val authority = context.resources.getString(R.string.authority)
    private val path = "pet"

    @RequiresApi(Build.VERSION_CODES.N)
    fun get(id : Int): CompletableFuture<Pet>{


        val uri = getBaseUri(app.resources)
                .appendPath(path)
                .appendPath(id.toString())
                .build()
                .toString()

        return getFromUri(uri)
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private fun getFromUri(uri: String, callback: Response.Listener<Pet>, error: Response.ErrorListener){


    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getFromUri(uri: String): CompletableFuture<Pet> {

        val pfpet = CompletableFuture<Pet>()

        app.requestQueue.add(JsonObjectRequest(
                uri,
                null,
                Response.Listener {
                    Log.i(TAG, "Got Pet object: ${it.toString(1)}")

                    pfpet.complete()
                },
                Response.ErrorListener {
                    Log.e(TAG, "Error getting message: ${it.message}")
                }
        ))
    }
}
