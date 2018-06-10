package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.*
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.PetFragmentPagerAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnConsultationListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnPetListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.OnTreatmentListListener

class PetActivity : BaseActivity(),
        OnPetListener,
        OnConsultationListListener,
        OnTreatmentListListener {


    companion object {
        const val TAG = "HPA/ACTIVITY/PET"

        fun startActivity(context: Context, pet : Pet? = null, id : Int? = null){
            val int = Intent(context, PetActivity::class.java)
            pet?.run {
                int.extras.putParcelable(context.resources.getString(R.string.pet), pet)
            }
            id?.run {
                int.extras.putInt("id", id)
            }
            context.startActivity(int)
        }
    }

    private lateinit var mPagerAdapter: PetFragmentPagerAdapter
    private lateinit var mViewPager: ViewPager

    private val petAccess by lazy { PetAccess(application.requestQueue) }
    private val consultationAccess by lazy { ConsultationAccess(application.requestQueue) }
    private val treatmentAccess by lazy { TreatmentAccess(application.requestQueue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pet)
        setContentView(R.layout.activity_pet)
        mPagerAdapter = PetFragmentPagerAdapter(supportFragmentManager, resources)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
    }

    override fun onPet(onPet: Response.Listener<Pet>) {
        val id = intent.getIntExtra("id", -1)
        if(id > 0) {
            val uri = getPetUri(resources, id).build().toString()
            petAccess.get(
                    uri,
                    onPet,
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                    }
            )
        }
    }

    override fun onConsultationList(list: Response.Listener<List<Consultation>>){
        val id = intent.getIntExtra("id", -1)
        if(id >  -1) {
            val uri = getPetConsultationUri(resources, id).build().toString()
            consultationAccess.getList(
                    uri,
                    list,
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                    }
            )
        }
    }

    override fun onTreatmentList(onList: Response.Listener<List<Treatment>>) {
        val id = intent.getIntExtra("id", -1)
        if(id  > -1){
            val uri = getPetTreatmentUri(resources, id).build().toString()
            treatmentAccess.getList(
                    uri,
                    onList,
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting treatment list from $uri: ${it.message}")
                    }
            )
        }
    }

}


