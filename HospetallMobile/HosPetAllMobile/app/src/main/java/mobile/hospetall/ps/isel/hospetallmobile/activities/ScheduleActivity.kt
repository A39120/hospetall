package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.EventFragmentPagerAdapter
import java.sql.Date
import java.time.Instant

class ScheduleActivity : AppCompatActivity() {

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

    fun getCurrentDate(): Long =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            Date.from(Instant.now()).time
        else
            System.currentTimeMillis()

}