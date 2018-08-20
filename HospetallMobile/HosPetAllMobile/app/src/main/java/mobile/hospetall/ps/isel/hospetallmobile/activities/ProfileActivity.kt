package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ProfileViewModel
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    companion object {
        fun start(context: Context){
            val int = Intent(context, ProfileActivity::class.java)
            context.startActivity(int)
        }
    }

    private lateinit var viewModel : ProfileViewModel
    private lateinit var mBinder : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.init()
        viewModel.getClient()?.observe(this, Observer {
            it?.apply {
                mBinder.client =it
                mBinder.executePendingBindings()
            }
        })

        mBinder.changeProfile.setOnClickListener { ChangeProfileActivity.start(this) }
    }
}