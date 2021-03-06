package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.EventFragmentPagerAdapter

class ScheduleActivity : BaseActivity() {

    companion object {
        const val TAG = "HPA/ACTIVITY/SCHEDULE"

        fun start(context: Context){
            val int = Intent(context, ScheduleActivity::class.java)
            context.startActivity(int)
        }

    }

    private lateinit var mPagerAdapter: EventFragmentPagerAdapter
    private lateinit var mViewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        mPagerAdapter = EventFragmentPagerAdapter(supportFragmentManager, resources)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
    }

}