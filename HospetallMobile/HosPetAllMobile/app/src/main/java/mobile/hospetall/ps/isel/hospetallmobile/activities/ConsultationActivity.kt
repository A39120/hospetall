package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ConsultationViewModel
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityConsultationBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ConsultationActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/CONSULT"
        private const val ID = "id"

        /**
         * Starts this activity.
         * @param id: [Consultation] id.
         */
        fun start(context: Context, id: Int) {
            val int = Intent(context, ConsultationActivity::class.java)
            int.putExtra(ID, id)
            context.startActivity(int)
        }
    }

    private lateinit var mBinding: ActivityConsultationBinding
    private lateinit var viewModel: ConsultationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultation)

        val id = intent.getIntExtra(ID, -1)
        if(id > 0) {
            val uri = UriUtils.getConsultationUri(id).build().toString()

            viewModel = ViewModelProviders.of(this).get(ConsultationViewModel::class.java)
            viewModel.init(uri)
            setConsultation()
        }
    }

    private fun setConsultation() {
        viewModel.getConsultation()?.observe(this, Observer {
            mBinding.setConsultation(it)
            mBinding.executePendingBindings()
        })

        viewModel.getPet()?.observe(this, Observer {
            mBinding.pet = it
            mBinding.executePendingBindings()
        })
    }
}
