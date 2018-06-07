package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.StringAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityTreatmentBinding
import mobile.hospetall.ps.isel.hospetallmobile.getTreatmentUri
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class TreatmentActivity : BaseActivity(){
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULT"

        fun startActivity(context: Context, id: Int){
            val int = Intent(context, TreatmentActivity::class.java)
            int.putExtra("id", id)
            context.startActivity(int)
        }
    }

    private val sAccess by lazy { StringAccess(application.requestQueue) }
    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue) }
    private val petAccess by lazy { PetAccess(application.requestQueue) }

    private lateinit var mBinding : ActivityTreatmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_treatment)
        setTreatment()
    }

    private fun setTreatment() {
        val treatment = intent.extras.getParcelable<Treatment?>(resources.getString(R.string.treatment))
        if(treatment != null) {
            mBinding.setTreatment(treatment)
            setNurse(treatment.nurseUri)
            setPet(treatment.petUri)
        } else {
            val id = intent.extras.getInt("id")
            val uri = getTreatmentUri(resources, id).build().toString()
            treatmentAccess.get(
                    uri,
                    Response.Listener {
                        Log.i(TAG, "Binding treatment ${it.id} to TreatmentActivity layout.")
                        mBinding.setTreatment(it)
                        setNurse(it.nurseUri)
                        setPet(it.petUri)
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting treatment from $uri: ${it.message}")
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
                            Log.i(TAG, "Binding pet ${it.id} to TreatmentActivity layout.")
                            mBinding.pet = pet
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Error getting pet from $petUri: ${it.message}")
                        }
                )
            }
        }
    }

    private fun setNurse(vetUri: String? = null) {
        val nurse = intent.extras.getString(resources.getString(R.string.nurse))
        if(nurse != null)
            mBinding.nurse = nurse
        else
            vetUri?.apply{
                sAccess.get(
                        vetUri,
                        Response.Listener {
                            Log.i(TAG, "Got vet with name $it")
                            mBinding.nurse = it
                        },
                        Response.ErrorListener {
                            Log.e(TAG, "Error getting nurse info from $vetUri")
                        }
                )
            }
    }
}