package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.EventListFragment
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.EventFragmentPagerAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.eventCache
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnEventListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import java.sql.Date
import java.time.Instant

class ScheduleActivity :
        AppCompatActivity(),
        OnEventListListener,
        OnPetListListener
{
    companion object {
        const val TAG = "HPA/ACTIVITY/SCHEDULE"

        fun start(context: Context){
            val int = Intent(context, ScheduleActivity::class.java)
            context.startActivity(int)
        }
    }

     private val scheduleAccess by lazy {
        ScheduleAccess( application.eventCache )
    }

    private val petAccess by lazy { PetAccess()}

    private lateinit var mPagerAdapter: EventFragmentPagerAdapter
    private lateinit var mViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        mPagerAdapter = EventFragmentPagerAdapter(supportFragmentManager, resources)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
    }

    override fun onEventListListener(listener: (List<Event>?) -> Unit, type: Int) {
        when(type){
            EventListFragment.FUTURE_EVENTS ->
                scheduleAccess.getAfter(getCurrentDate(), listener)
            EventListFragment.PAST_EVENTS ->
                scheduleAccess.getBefore(getCurrentDate(), listener)
            else -> scheduleAccess.getAll(listener)
        }
    }

    fun getCurrentDate(): Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Date.from(Instant.now()).time
        else
            System.currentTimeMillis()

    override fun onPetList(listener: (List<Pet>) -> Unit) {
        petAccess.getList(
                UriUtils.getClientsPetsUri(resources, getId()).build().toString(),
                Response.Listener(listener),
                Response.ErrorListener {
                    Log.e(TAG, "Failed to get pet list to accompany the events.")
                }
        )
    }
}