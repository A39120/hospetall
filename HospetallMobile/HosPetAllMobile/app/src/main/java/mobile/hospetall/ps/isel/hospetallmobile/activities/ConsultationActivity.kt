package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.StringAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityConsultationBinding
import mobile.hospetall.ps.isel.hospetallmobile.getConsultationUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class ConsultationActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULT"

        fun startActivity(context: Context, id: Int){
            val int = Intent(context, ConsultationActivity::class.java)
            int.putExtra("id", id)
            context.startActivity(int)
        }
    }

    private val sAccess by lazy { StringAccess(application.requestQueue) }
    private val consultationAccess by lazy { ConsultationAccess(application.requestQueue) }
    private val petAccess by lazy { PetAccess(application.requestQueue) }

    private lateinit var mBinding : ActivityConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultation)
        setConsultation()
    }

    private fun setConsultation() {
        val consultation = intent.extras.getParcelable(resources.getString(R.string.consultation)) as Consultation?
        if(consultation != null) {
            mBinding.setConsultation(consultation)
            setVeterinarian(consultation.vetUri)
            setPet(consultation.petUri)
        } else {
            val id = intent.extras.getInt("id")
            val uri = getConsultationUri(resources, id).build().toString()
            consultationAccess.get(
                    uri,
                    Response.Listener {
                        Log.i(TAG, "Binding consultation ${it.id} to ConsultationActivity layout.")
                        mBinding.setConsultation(it)
                        setVeterinarian(it.vetUri)
                        setPet(it.petUri)
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation from $uri: ${it.message}")
                    }
                )
            }
    }

    private fun setPet(petUri: String? = null) {
        val pet =  intent.extras.getParcelable(resources.getString(R.string.pet)) as Pet?
        if(pet != null)
            mBinding.pet = pet
        else {
            petUri?.apply {
                petAccess.get(
                    this,
                        Response.Listener {
                            Log.i(TAG, "Binding pet ${it.id} to ConsultationActivity layout.")
                            mBinding.pet = pet
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Error getting pet from $petUri: ${it.message}")
                        }
                )
            }
        }
    }

    private fun setVeterinarian(vetUri: String? = null) {
        val vet = intent.extras.getString(resources.getString(R.string.veterinarian)) as String?
        if(vet != null)
            mBinding.veterinarian = vet
        else
            vetUri?.apply{
                sAccess.get(
                    vetUri,
                    Response.Listener {
                        Log.i(TAG, "Got vet with name $it")
                        mBinding.veterinarian = it
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting veterinarian info from $vetUri")
                    }
                )
            }
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation)

        val layout = findViewById<RelativeLayout>(R.id.main)
        val procholder = ConsultationHolder(layout)

        val extras = intent.extras
        val consultation = extras.getParcelable("consultation") as Consultation?
        val pet = extras.getParcelable("pet") as Pet?

        if(consultation == null) {
            val id = extras.getInt("id")
            consultationAccess.get(
                    getConsultationUri(resources, id).toString(),
                    Response.Listener {
                        pet?.apply{
                            it.vetUri.let {
                                sAccess.get(

                                )

                            }
                        }
                    },
                    Response.ErrorListener {  }
            )
        }
        consultation?.apply {
            pet?.run {

            }
        }
    }

    private fun petCallback(consultation: Consultation,
                            onSuccess: Response.Listener<Consultation> ) =
            consultation.petUri?.apply {
                    petAccess.get(
                            this,
                            Response.Listener {
                                val pet = it
                                Log.i(TAG, "Got pet (${it.id}) for consultation activity.")
                                procholder.parseToConsultation(resources, it, this)
                            },
                            Response.ErrorListener {
                                Log.e(TAG, "Error getting pet from $this")
                            }
                )
            }

    private fun vetCallback(consultation: Consultation,
                            pet: Pet,
                            onSuccess: Response.Listener<String>) =
        consultation.vetUri?.apply {
            sAccess.get(
                    this,
                    Response.Listener {
                        it?.let {
                            Log.i(TAG, "Got veterinarian name: $it")
                            onSuccess.onResponse(it)
                        }
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting veterinarian name from uri: $this")
                    }
            )
        }


    fun checkIfPet(){

    }

    fun parseToView(){

    }
            */
}

