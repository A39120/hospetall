package mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment

import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment

class EventFragmentPagerAdapter(
        fm: FragmentManager,
        private val resources: Resources
) : FragmentPagerAdapter(fm) {

    val FUTURE_INDEX = 0
    val PAST_INDEX = 1

    override fun getItem(position: Int): Fragment {
        val frag = EventListFragment()
        val bundle = Bundle()

        when (position) {
            FUTURE_INDEX ->
                bundle.putInt(EventListFragment.TYPE, EventListFragment.FUTURE_EVENTS)
            PAST_INDEX ->
                bundle.putInt(EventListFragment.TYPE, EventListFragment.PAST_EVENTS)

            else -> throw IllegalArgumentException()
        }
        frag.arguments = bundle
        return frag
    }


    override fun getPageTitle(position: Int) = when(position) {
        FUTURE_INDEX -> resources.getString(R.string.title_future_events)
        PAST_INDEX -> resources.getString(R.string.title_past_events)
        else -> ""
    }


    override fun getCount() = 2
}
