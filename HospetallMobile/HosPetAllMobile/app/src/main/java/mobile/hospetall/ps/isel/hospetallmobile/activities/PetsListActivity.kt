package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.PetListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.adapter.PetsAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
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

    private lateinit var viewModel: PetListViewModel
    private lateinit var recycler : RecyclerView
    private var adapter : PetsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val id = getId()
        viewModel = ViewModelProviders.of(this).get(PetListViewModel::class.java)
        viewModel.init(id)

        recycler = findViewById(R.id.list)

        Log.i(TAG, "Getting pet list from owner with id: $id.")

        viewModel.getPetList()?.observe(this, Observer {
            Log.i(TAG, "Adapting pet list of client $id to adapter.")
            it?.apply {
                if(adapter == null){
                    createAdapter(it)
                } else {
                    adapter!!.setPetList(it)
                }
            }
        })
    }

    private fun createAdapter(petList: List<Pet>) {
        adapter = PetsAdapter(this@PetsListActivity, petList)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this@PetsListActivity)

    }

}