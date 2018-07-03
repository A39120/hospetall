package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.adapter.fragment.PetFragmentPagerAdapter
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnConsultationListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnPetListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnTreatmentListListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class PetActivity : BaseActivity(),
        OnPetListener,
        OnConsultationListListener,
        OnTreatmentListListener {

    companion object {
        const val TAG = "HPA/ACTIVITY/PET"

        private const val PET = "pet"
        private const val ID = "id"

        fun start(context: Context, pet: Pet){
            val int = Intent(context, PetActivity::class.java)
            int.putExtra(PET, pet)
            context.startActivity(int)
        }

        fun start(context: Context, id : Int){
            val int = Intent(context, PetActivity::class.java)
            int.extras.putInt(ID, id)
            context.startActivity(int)
        }
    }

    private lateinit var mPagerAdapter: PetFragmentPagerAdapter
    private lateinit var mViewPager: ViewPager

    private var pet: Pet? = null
    private var id : Int = -1

    private val petAccess by lazy { PetAccess() }
    private val consultationAccess by lazy { ConsultationAccess() }
    private val treatmentAccess by lazy { TreatmentAccess() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pet)
        setContentView(R.layout.activity_pet)

        pet = intent.getParcelableExtra<Pet?>(PET)
        id = pet?.id?: intent.getIntExtra(ID, -1)

        mPagerAdapter = PetFragmentPagerAdapter(supportFragmentManager, resources)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = mPagerAdapter
    }

    override fun onPet(onPet: (Pet) -> Unit) {
        if(pet != null) {
            val immutablePet = pet!!
            onPet(immutablePet)
        } else if(id > 0) {
            val uri = UriUtils.getPetUri(resources, id).build().toString()
            petAccess.get(
                    uri,
                    Response.Listener {
                        pet = it
                        onPet(it)
                    },
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting pet from $uri: ${it.message}")
                    }
            )
        }
    }

    override fun onConsultationList(list: (List<Consultation>) -> Unit){
        if(id >  0) {
            val uri = UriUtils.getPetConsultationUri(resources, id).build().toString()
            consultationAccess.getList(
                    uri,
                    Response.Listener(list),
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting consultation list from $uri: ${it.message}")
                    }
            )
        }
    }

    override fun onTreatmentList(onList: (List<Treatment>) -> Unit) {
        if(id  > 0){
            val uri = UriUtils.getPetTreatmentUri(resources, id).build().toString()
            treatmentAccess.getList(
                    uri,
                    Response.Listener(onList),
                    Response.ErrorListener {
                        Log.e(TAG, "Error getting treatment list from $uri: ${it.message}")
                    }
            )
        }
    }
}


