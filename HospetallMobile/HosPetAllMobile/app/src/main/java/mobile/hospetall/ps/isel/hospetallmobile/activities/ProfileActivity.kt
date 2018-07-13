package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.ProfileViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ClientAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityProfileBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HPA/ACTIVITY/PROFILE"
        const val CLIENT = "client"

        fun start(context: Context, client: Client? = null){
            val int = Intent(context, ProfileActivity::class.java)
            client?.apply { int.putExtra(CLIENT, client) }
            context.startActivity(int)
        }

    }

    private lateinit var mBinder : ActivityProfileBinding
    private val clientAccess by lazy { ClientAccess() }
    private lateinit var viewModel : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = getId()
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.init(id)
        viewModel.getClient()?.observe(this, Observer {
            it?.apply {
                setClientInfo(it)
             }
        })

        mBinder.updateProfileButton.setOnClickListener{update()}
        mBinder.executePendingBindings()
    }

    /**
     * Updates the changed client information
     */
    private fun update() {
        val button = mBinder.updateProfileButton
        button.progress = 1

        val uri = UriUtils.getClientUri(getId())
        val newClient = Client(
                uri.build().toString(),
                getId(),
                mBinder.updateClientLastName.text.toString(),
                mBinder.updateClientGivenName.text.toString(),
                mBinder.updateClientEmail.text.toString(),
                mBinder.updateClientTelephone.text.toString(),
                mBinder.updateClientAddress.text.toString(),
                mBinder.updateClientPostalCode.text.toString(),
                Integer.parseInt(mBinder.updateClientNif.text.toString())
        )

        val req = clientAccess.put(
                newClient.uri,
                newClient.toJSON(),
                Response.Listener {
                    Log.i(TAG, "Client updated.")
                    button.progress = 100
                    button.setOnClickListener { update() }
                },
                Response.ErrorListener {
                    Log.e(TAG, "Failed to update client.")
                    button.progress = -1
                    button.setOnClickListener { update() }
                }
        )

        button.setOnClickListener {
            req?.cancel()
            button.progress = 0
        }
    }

    fun cancel(){
        this.finish()
    }

    fun setButtonActive(view: View) {
       mBinder.updateProfileButton.isClickable = true
    }

    private fun setClientInfo(client : Client) {
        mBinder.apply {
            updateClientLastName.setText(client.familyName, TextView.BufferType.EDITABLE)
            updateClientGivenName.setText(client.givenName, TextView.BufferType.EDITABLE)
            updateClientEmail.setText(client.email, TextView.BufferType.EDITABLE)
            updateClientTelephone.setText(client.telephone, TextView.BufferType.EDITABLE)
            updateClientAddress.setText(client.address, TextView.BufferType.EDITABLE)
            client.postalCode?.let{
                updateClientPostalCode.setText(it, TextView.BufferType.EDITABLE)
            }
            updateClientNif.setText(client.nif.toString(), TextView.BufferType.EDITABLE)
        }

    }
}