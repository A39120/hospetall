package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response

import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.databinding.FragmentPetDetailBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet


/**
 * Activities that contain this fragment must implement the
 * [PetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetFragment : Fragment() {
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
        (activity as PetActivity).onPet(Response.Listener {
            Log.i(TAG, "Binding pet to pet detail fragment.")
            binder.pet = it
        })

        return binder.root
    }

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach called.")
        val pet = arguments?.getParcelable("pet") as Pet?
        if(pet != null){
            binder.pet = pet
        } else {
            Log.e(TAG, "Could not attach pet information.")
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
    */

}
