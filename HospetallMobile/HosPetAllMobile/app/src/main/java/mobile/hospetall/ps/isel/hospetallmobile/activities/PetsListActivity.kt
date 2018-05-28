package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.*
import mobile.hospetall.ps.isel.hospetallmobile.adapter.PetsAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess

class PetsListActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/PET_LIST"
    }

    private val petAccess: PetAccess by lazy { PetAccess(application.requestQueue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)

        val id = getId()
        Log.i(TAG, "Getting pet list from owner with id: $id.")

        val uri = getClientsPetsUri(resources, id)
        petAccess.getList(
                uri.toString(),
                "petList",
                Response.Listener{
                    Log.i(TAG, "Adapting pet list of client $id to adapter.")
                    val list = findViewById<RecyclerView>(R.id.pet_list_view)
                    list.adapter = PetsAdapter(this, it)
                    list.layoutManager = LinearLayoutManager(this@PetsListActivity)
                },
                Response.ErrorListener{
                    Log.e(TAG, "Error getting data from client $id pets ($uri): ${it.message}")
                }
        )
    }

}