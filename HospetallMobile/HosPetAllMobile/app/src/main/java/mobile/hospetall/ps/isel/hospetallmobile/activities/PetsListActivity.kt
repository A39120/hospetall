package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.PetsAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.database
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

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

    private val petAccess: PetAccess by lazy { PetAccess() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val id = getId()
        Log.i(TAG, "Getting pet list from owner with id: $id.")

        val uri = UriUtils.getClientsPetsUri(resources, id)
        petAccess.getList(
                uri.toString(),
                Response.Listener{
                    Log.i(TAG, "Adapting pet list of client $id to adapter.")
                    val list = findViewById<RecyclerView>(R.id.list)
                    list.adapter = PetsAdapter(this, it)
                    list.layoutManager = LinearLayoutManager(this@PetsListActivity)
                },
                Response.ErrorListener{
                    Log.e(TAG, "Error getting data from client $id pets ($uri): ${it.message}")
                }
        )
    }

}