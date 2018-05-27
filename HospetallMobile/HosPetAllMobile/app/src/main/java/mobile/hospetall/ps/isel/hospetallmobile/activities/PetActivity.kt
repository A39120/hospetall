package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ConsultationAdapter
import mobile.hospetall.ps.isel.hospetallmobile.adapter.TreatmentAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.getPetUri
import mobile.hospetall.ps.isel.hospetallmobile.layout.adaptPetDetailLayout
import mobile.hospetall.ps.isel.hospetallmobile.layout.getPetDetailHolder
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class PetActivity : AppCompatActivity() {

    private val consultationAccess by lazy { ConsultationAccess(application.requestQueue)    }
    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue)    }
    private val petAccess by lazy { PetAccess(application.requestQueue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)

        val pet = intent.extras.getParcelable("pet") as Pet?

        if(pet != null) displayPet(pet)
        else {
            val id = intent.extras.getInt("id")
            getPet(id)
        }

    }

    private fun displayPet(pet: Pet) {
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
        val uri = getPetUri(resources, id).build()
        petAccess.get(
                uri,
                Response.Listener { displayPet(it) },
                Response.ErrorListener { }
        )
    }

    private fun getConsultations(uri: Uri) {
        val layout = findViewById<RelativeLayout>(R.id.consultation)
        val list = layout.findViewById<ListView>(R.id.list)

        consultationAccess.getList(
                uri,
                "consultationList",
                Response.Listener {
                    layout.findViewById<TextView>(R.id.label).text = resources.getString(R.string.consultation)
                    list.adapter = ConsultationAdapter(this, it.toTypedArray())
                },
                Response.ErrorListener { }
        )
    }

    private fun getTreatment(uri: Uri) {
        val layout = findViewById<RelativeLayout>(R.id.treatment)
        val list = layout.findViewById<ListView>(R.id.list)

        treatmentAccess.getList(
                uri,
                "treatmentList",
                Response.Listener {
                    layout.findViewById<TextView>(R.id.label).text = resources.getString(R.string.treatment)
                    list.adapter = TreatmentAdapter(this, it.toTypedArray())
                },
                Response.ErrorListener { }
        )
    }

}
