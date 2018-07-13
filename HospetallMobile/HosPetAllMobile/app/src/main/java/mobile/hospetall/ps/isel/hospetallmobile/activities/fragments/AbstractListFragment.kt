package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R

abstract class AbstractListFragment
    : BaseFragment() {
    companion object {
        const val SHOW_PET = "show_pet"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.fragment_procedure_list, container, false)
        callbackInfo(rootView)
        return rootView
    }

    abstract  fun callbackInfo(view: View)

    abstract fun getTitle() : Int
}