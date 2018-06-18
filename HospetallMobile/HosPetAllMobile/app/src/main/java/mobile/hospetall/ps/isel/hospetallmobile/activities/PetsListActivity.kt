package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.PetsAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.database
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId

/**
 * Activity to show the pet list. It will bind the
 * [PetsAdapter] to the Recycler View and use
 * [PetAccess] to obtain the information for each pet.
 */
class PetsListActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/PET_LIST"

        fun start(context: Context){
            val int = Intent(context, PetsListActivity::class.java)
            context.startActivity(int)
        }
    }

    private val petAccess: PetAccess by lazy { PetAccess(application.requestQueue, application.database) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)

        val id = getId()
        Log.i(TAG, "Getting pet list from owner with id: $id.")


        val uri = UriUtils.getClientsPetsUri(resources, id)
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

    fun createPet(ignore : View) {
        AddPetActivity.start(this.baseContext)
    }

}