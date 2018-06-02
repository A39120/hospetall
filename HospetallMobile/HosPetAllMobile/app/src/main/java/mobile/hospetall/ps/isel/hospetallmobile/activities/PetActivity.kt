package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.getPetUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class PetActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/PET"
    }

    private val consultationAccess by lazy { ConsultationAccess(application.requestQueue)    }
    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue)    }
    private val petAccess by lazy { PetAccess(application.requestQueue) }

    private lateinit var mBinding : ActivityPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pet)
        Log.i(TAG, "OnCreate called. Binding view to pet.")

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pet)
        val pet = intent.extras.getParcelable("pet") as Pet?

        if(pet != null)
            mBinding.pet = pet
        else {
            val id = intent.extras.getInt("id")
            val uri = getPetUri(resources, id).build()
            petAccess.get(
                    uri.toString(),
                    Response.Listener {
                        Log.i(TAG, "Got pet with $id from $uri.")
                        mBinding.pet = pet
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Failed to get pet with $id from $uri.")
                    })
        }
        /*if(pet != null)
            displayPet(pet)
        else {
            val id = intent.extras.getInt("id")
            getPet(id)
        }*/

    }
/*
    private fun displayPet(pet: Pet) {
        Log.i(TAG, "Displaying pet ${pet.id} details.")
        val name = findViewById<TextView>(R.id.name)
        val detail = findViewById<RelativeLayout>(R.id.pet_detail)
        val holder = getPetDetailHolder(detail)
        name.text = pet.name
        adaptPetDetailLayout(pet, holder)

        if(pet.consultationUri != null)
            getConsultations(Uri.parse(pet.consultationUri))

        if(pet.treatmentUri != null)
            getTreatment(Uri.parse(pet.treatmentUri))

    }

    private fun getPet(id: Int) {
        Log.i(TAG, "Getting pet id details.")
        val uri = getPetUri(resources, id).build()
        petAccess.get(
                uri.toString(),
                Response.Listener { displayPet(it) },
                Response.ErrorListener { }
        )
    }

    private fun getConsultations(uri: Uri) {
        Log.i(TAG, "Getting consultations from uri: $uri")
        val layout = findViewById<RelativeLayout>(R.id.consultation)
        val list = layout.findViewById<RecyclerView>(R.id.list)

        consultationAccess.getList(
                uri.toString(),
                "consultationList",
                Response.Listener {
                    Log.i(TAG, "Got consultation list from $uri")
                    layout.findViewById<TextView>(R.id.label).text = resources.getString(R.string.consultation)
                    list.adapter = ProcedureAdapter(this, it)
                    list.layoutManager = LinearLayoutManager(this@PetActivity)
                },
                Response.ErrorListener {
                    Log.w(TAG, "Error getting the consultation list from uri: $uri. Error message: ${it.message}")
                }
        )
    }

    private fun getTreatment(uri: Uri) {
        Log.i(TAG, "Getting treatments from uri: $uri")
        val layout = findViewById<RelativeLayout>(R.id.treatment)
        val list = layout.findViewById<RecyclerView>(R.id.list)

        treatmentAccess.getList(
                uri.toString(),
                "treatmentList",
                Response.Listener {
                    Log.i(TAG, "Got treatment list from $uri")
                    layout.findViewById<TextView>(R.id.label).text = resources.getString(R.string.treatment)
                    list.adapter = ProcedureAdapter(this, it)
                    list.layoutManager = LinearLayoutManager(this@PetActivity)
                },
                Response.ErrorListener {
                    Log.w(TAG, "Error getting the treatment list from uri: $uri. Error message: ${it.message}")
                }
        )
    }
*/

}
