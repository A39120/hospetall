package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/MAIN"

        fun start(context: Context) {
            val int = Intent(context, MainActivity::class.java)
            context.startActivity(int)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitiy)
    }
}
