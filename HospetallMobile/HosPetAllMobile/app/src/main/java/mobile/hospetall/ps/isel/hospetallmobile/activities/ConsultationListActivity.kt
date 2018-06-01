package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R

class ConsultationListActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULTATION_LIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_pet_procedures)

        val all = intent.extras.getBoolean(EXTRA_ALL_PETS)
        if(all) {
            //TODO
        } else {
            //TODO
        }
    }
}