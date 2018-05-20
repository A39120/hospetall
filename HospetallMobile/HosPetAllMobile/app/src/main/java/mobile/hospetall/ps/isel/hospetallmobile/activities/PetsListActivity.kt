package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.PetsAdapter
import mobile.hospetall.ps.isel.hospetallmobile.baseUri
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.getId
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class PetsListActivity : BaseActivity() {
    companion object {
        private const val TAG = "ACTIVITY/PET_LIST"
    }

    private val petAccess: PetAccess by lazy { PetAccess(application.requestQueue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)

        val id = getId()
        val list = findViewById<ListView>(R.id.pet_list_view)

        Log.i(TAG, "Getting pet list from owner with id: $id.")

        val uri = this.application.baseUri
                .appendPath("client")
                .appendEncodedPath(id.toString())
                .appendPath("pets")
                .build()

        petAccess.getList(
                uri,
                "petList",
                Response.Listener{
                    Log.i(TAG, "Adapting pet list of client $id to adapter.")
                    list.adapter = PetsAdapter(this, it.toTypedArray())
                },
                Response.ErrorListener{
                    Log.e(TAG, "Error getting data from client $id pets.")
                }
        )
    }

}