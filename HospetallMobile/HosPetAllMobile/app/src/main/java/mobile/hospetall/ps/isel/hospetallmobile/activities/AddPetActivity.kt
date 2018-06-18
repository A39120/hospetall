package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import com.dd.processbutton.iml.ActionProcessButton
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.database
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityAddPetBinding
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import org.json.JSONObject

/**
 * Activity for adding a pet to the api.
 */
class AddPetActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/ADD_PET"

        fun start(context: Context) {
            val int = Intent(context, AddPetActivity::class.java)
            context.startActivity(int)
        }
    }

    private val petAccess by lazy { PetAccess(application.requestQueue, application.database) }

    private lateinit var mBinding: ActivityAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate called.")
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_pet)
    }

    fun onClickAddPet() {
        val button = findViewById<ActionProcessButton>(R.id.add_pet_button)

        button.progress = 1

        //TODO: Fix this.
        val name = mBinding.addPetName.text.toString()
        val birth = mBinding.addPetBirthdate.text.toString()
        val chip = Integer.parseInt(mBinding.addPetChip.text.toString())
        val race = mBinding.addPetRace.text.toString() as String?
        val species = mBinding.addPetSpecies.text.toString()

        val json = JSONObject()
                .accumulate(resources.getString(R.string.json_pet_name), name)
                .accumulate(resources.getString(R.string.json_pet_birthdate), birth)
                .accumulate(resources.getString(R.string.json_pet_chip), chip)
                //.accumulate(resources.getString(R.string.json_pet_race), race)
                //.accumulate(resources.getString(R.string.json_pet_species), species)

        val uri = UriUtils.getPetListUri(resources).toString()

        Log.i(TAG, "Requesting a post to create a new pet.")
        val request = petAccess.post(
                uri,
                json,
                Response.Listener {
                    Log.i(TAG, "Concluded post for pet. Response: $it")
                    button.progress = 100
                    goToPreviousActivity()
                },
                Response.ErrorListener {
                    Log.e(TAG, "Error on post on $uri: ${it.message}")
                    button.progress = -1
                    goToPreviousActivity()
                }
        )

        button.setOnClickListener {
            request?.cancel()
            button.progress = 0
            button.setOnClickListener{onClickAddPet() }
        }
    }

    /**
     * Goes to the previous activity in the stack.
     */
    private fun goToPreviousActivity(){
        finish()
    }

}