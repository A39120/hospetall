package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.PetFragmentPagerAdapter

class PetActivity : BaseActivity() {

    companion object {
        const val TAG = "HPA/ACTIVITY/PET"

        private const val ID = "id"

        fun start(context: Context, id: Int) {
            val int = Intent(context, PetActivity::class.java)
            int.putExtra(ID, id)
            context.startActivity(int)
        }

    }

    private lateinit var mPagerAdapter: PetFragmentPagerAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet)

        val id = intent.getIntExtra(ID, -1)
        mPagerAdapter = PetFragmentPagerAdapter(supportFragmentManager, resources, id)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
    }
}




