package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.PetActivity
import mobile.hospetall.ps.isel.hospetallmobile.adapter.ProcedureAdapter
import mobile.hospetall.ps.isel.hospetallmobile.models.Procedure

abstract class AbstractProcedureListFragment<T : Procedure> : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.fragment_procedure_list, container, false)
        callbackInfo(rootView)
        return rootView
    }

    abstract fun callbackInfo(view : View)
    abstract fun getTitle() : Int
}