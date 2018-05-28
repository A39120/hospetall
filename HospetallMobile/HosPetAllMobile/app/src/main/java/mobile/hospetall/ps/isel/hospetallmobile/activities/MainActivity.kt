package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import android.util.Log
import com.android.volley.Response
import kotlinx.android.synthetic.main.activity_main_activitiy.*
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/MAIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitiy)
    }
}
