package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.PetViewModel
import mobile.hospetall.ps.isel.hospetallmobile.databinding.FragmentPetDetailBinding


/**
 * Pet fragment to display the [Pet] information.
 */
class PetFragment : BaseFragment() {
    companion object {
        const val TITLE = R.string.pet
        const val TAG = "HPA/FRAGMENT/PET"
        const val URI = "pet_uri"
    }

    /**
     * Layout fragment binder that binds the information to the
     * layout.
     */
    private lateinit var binder : FragmentPetDetailBinding
    private lateinit var viewModel : PetViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val uri = arguments?.getString(URI)
        if(uri == null){
            //TODO: Error
            return
        }

        viewModel = ViewModelProviders.of(this)
                .get(PetViewModel::class.java)
        viewModel.init(uri)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.i(TAG, "onCreateView called.")
        binder = FragmentPetDetailBinding.inflate(inflater, container, false)

        viewModel.getPet()?.observe(this, Observer {
            Log.i(TAG, "Pet information changed.")
            binder.pet = it
            binder.executePendingBindings()
        })

        return binder.root
    }
}
