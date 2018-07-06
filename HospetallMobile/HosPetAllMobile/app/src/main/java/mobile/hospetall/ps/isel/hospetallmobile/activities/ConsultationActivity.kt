package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityConsultationBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ConsultationActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULT"

        private const val ID = "id"
        private const val CONSULTATION = "consultation"
        private const val PET = "pet"

        /**
         * Starts this activity.
         * @param id: [Consultation] id.
         */
        fun start(context: Context, id: Int) {
            val int = Intent(context, ConsultationActivity::class.java)
            int.putExtra(ID, id)
            context.startActivity(int)
        }

        /**
         * Starts this activity with the consultation.
         * @param consultation: [Consultation] object.
         */
        fun start(context: Context, consultation: Consultation, pet: Pet? = null) {
            val int = Intent(context, ConsultationActivity::class.java)
            int.putExtra(CONSULTATION, consultation)
            pet?.let { int.putExtra(PET, pet) }
            context.startActivity(int)
        }
    }

    //private val sAccess by lazy { StringAccess(application.requestQueue) }
    private val consultationAccess by lazy { ConsultationAccess(application as HospetallApplication) }
    private val petAccess by lazy { PetAccess(application as HospetallApplication) }

    private lateinit var mBinding: ActivityConsultationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultation)
        setConsultation()
    }

    private fun setConsultation() {
        val consultation = intent.extras.getParcelable(CONSULTATION) as Consultation?
        if (consultation != null) {
            mBinding.setConsultation(consultation)
            //setVeterinarian(consultation.vetUri)
            setPet(consultation.petUri)
        } else {
            val id = intent.extras.getInt(ID)
            val uri = UriUtils.getConsultationUri(resources, id).build().toString()
            consultationAccess.get(
                    uri,
                    Response.Listener {
                        Log.i(TAG, "Binding consultation ${it.id} to ConsultationActivity layout.")
                        mBinding.setConsultation(it)
                        //setVeterinarian(it.vetUri)
                        setPet(it.petUri)
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation from $uri: ${it.message}")
                    }
            )
        }
    }

    private fun setPet(petUri: String? = null) {
        val pet = intent.extras.getParcelable(PET) as Pet?
        if (pet != null)
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
}

