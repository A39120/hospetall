package mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment

import android.content.res.Resources
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.ConsultationListFragment
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.PetFragment
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.TreatmentListFragment


class PetFragmentPagerAdapter(
        fm: FragmentManager,
        private val resources: Resources
) : FragmentPagerAdapter(fm) {

    val PET_INDEX = 0
    val CONSULTATION_INDEX = 1
    val TREATMENT_INDEX = 2

    override fun getItem(position: Int) =
            when(position){
                PET_INDEX -> PetFragment()
                CONSULTATION_INDEX -> ConsultationListFragment()
                TREATMENT_INDEX -> TreatmentListFragment()
                else -> throw IllegalArgumentException()
            }


    override fun getPageTitle(position: Int) = when(position) {
        PET_INDEX -> resources.getString(PetFragment.TITLE)
        CONSULTATION_INDEX -> resources.getString(ConsultationListFragment.TITLE)
        TREATMENT_INDEX -> resources.getString(TreatmentListFragment.TITLE)
        else -> ""
    }


    override fun getCount() = 3
}
