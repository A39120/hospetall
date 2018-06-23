package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.databinding.FragmentPetDetailBinding
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListener


/**
 * Pet fragment to display the [Pet] information.
 */
class PetFragment : BaseFragment() {
    companion object {
        const val TITLE = R.string.pet
        const val TAG = "HPA/FRAGMENT/PET"
    }

    private lateinit var binder : FragmentPetDetailBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView called.")

        binder = FragmentPetDetailBinding.inflate(inflater, container, false)
        //Passing the callback to pet after getting the result
        (activity as OnPetListener).onPet(
                {
                    Log.i(TAG, "Binding pet ${it.id} to pet detail fragment.")
                    binder.pet = it
                    binder.executePendingBindings()
                }
        )

        return binder.root
    }
}
