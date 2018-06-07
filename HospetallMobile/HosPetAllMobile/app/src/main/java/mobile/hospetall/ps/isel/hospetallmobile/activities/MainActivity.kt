package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/MAIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitiy)
    }
}
