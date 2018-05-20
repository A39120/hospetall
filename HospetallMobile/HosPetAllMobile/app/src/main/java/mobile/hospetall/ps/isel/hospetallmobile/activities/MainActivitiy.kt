package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main_activitiy.*
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess

class MainActivitiy : AppCompatActivity() {
    companion object {
        const val TAG = "ACTIVITY/MAIN"
    }

    private val access: PetAccess by lazy {PetAccess(this.applicationContext)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitiy)

        Log.i(TAG, "Getting Pet with id: 1")
        this.text_view_1.text = access.get(1);


    }

    
}
