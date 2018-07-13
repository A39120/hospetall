package mobile.hospetall.ps.isel.hospetallmobile.activities

//import mobile.hospetall.ps.isel.hospetallmobile.utils.getTreatmentUri
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.TreatmentViewModel
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityTreatmentBinding
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class TreatmentActivity : BaseActivity(){
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULT"

        private const val ID = "id"

        fun start(context: Context, id: Int){
            val int = Intent(context, TreatmentActivity::class.java)
            int.putExtra(ID, id)
            context.startActivity(int)
        }

    }

    private lateinit var mBinding : ActivityTreatmentBinding
    private lateinit var viewModel : TreatmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_treatment)

        val id = intent.getIntExtra(ID, -1)
        if(id < 0) {
            //TODO: Error
        }

        val uri = UriUtils.getTreatmentUri(id).build().toString()
        viewModel = ViewModelProviders.of(this).get(TreatmentViewModel::class.java)
        viewModel.init(uri)

        //Setting treatment
        viewModel.getTreatment()?.observe(this, Observer {
            mBinding.setTreatment(it)
            mBinding.executePendingBindings()
        })

        //Setting pet
        viewModel.getPet()?.observe(this, Observer {
            mBinding.pet = it
            mBinding.executePendingBindings()
        })

    }


}