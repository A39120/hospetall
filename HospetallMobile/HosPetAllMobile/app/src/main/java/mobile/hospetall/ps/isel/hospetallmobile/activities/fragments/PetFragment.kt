package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.databinding.FragmentPetDetailBinding
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnPetListener

class PetFragment : BaseFragment() {
    companion object {
        const val TITLE = R.string.pet
        const val TAG = "HPA/FRAGMENT/PET"
    }

    private lateinit var binder : FragmentPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate called.")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.i(TAG, "onCreateView called.")

        binder = FragmentPetDetailBinding.inflate(inflater, container, false)
        (activity as OnPetListener).onPet(Response.Listener {
            Log.i(TAG, "Binding pet ${it.id} to pet detail fragment.")
            binder.pet = it
        })

        return binder.root
    }
}
