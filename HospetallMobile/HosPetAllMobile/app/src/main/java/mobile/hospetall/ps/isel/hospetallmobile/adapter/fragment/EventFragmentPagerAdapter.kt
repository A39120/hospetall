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

    companion object {
        const val PERIODIC_INDEX = 0
        const val FUTURE_INDEX = 1
        const val PAST_INDEX = 2
    }

    override fun getItem(position: Int): Fragment {
        val frag = EventListFragment()
        val bundle = Bundle()

        when (position) {
            PERIODIC_INDEX ->
                bundle.putInt(EventListFragment.TYPE, EventListFragment.PERIODIC_EVENTS)
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
        PERIODIC_INDEX -> resources.getString(R.string.title_periodic_events)
        FUTURE_INDEX -> resources.getString(R.string.title_future_events)
        PAST_INDEX -> resources.getString(R.string.title_past_events)
        else -> ""
    }


    override fun getCount() = 3
}
