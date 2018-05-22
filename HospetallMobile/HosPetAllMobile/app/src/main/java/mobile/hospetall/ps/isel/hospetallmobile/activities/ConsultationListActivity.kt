package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R

class ConsultationListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_procedures)

        val all = intent.extras.getBoolean(EXTRA_ALL_PETS)
        if(all) {
            //TODO
        } else {
            //TODO
        }
    }
}