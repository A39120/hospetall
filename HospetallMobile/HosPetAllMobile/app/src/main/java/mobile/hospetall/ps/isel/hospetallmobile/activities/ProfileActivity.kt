package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ClientAccess
import mobile.hospetall.ps.isel.hospetallmobile.database
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityProfileBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.requestQueue
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.listeners.OnClientListener
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ProfileActivity : AppCompatActivity(), OnClientListener {

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
    private val clientAccess : ClientAccess by lazy { ClientAccess(application.requestQueue, application.database) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        onClient {
            mBinder.client = it
            mBinder.updateProfileButton.setOnClickListener{update()}
            mBinder.executePendingBindings()
        }
    }

    /**
     * Updates the changed client information
     */
    private fun update() {
        val button = mBinder.updateProfileButton
        button.progress = 1

        val uri = UriUtils.getClientUri(resources, getId())
        val newClient = Client(
                uri.build().toString(),
                getId(),
                findViewById<EditText>(R.id.update_client_lastName).text.toString(),
                findViewById<EditText>(R.id.update_client_givenName).text.toString(),
                findViewById<EditText>(R.id.update_client_email).text.toString(),
                findViewById<EditText>(R.id.update_client_telephone).text.toString(),
                findViewById<EditText>(R.id.update_client_address).text.toString(),
                findViewById<EditText>(R.id.update_client_postalCode).text.toString(),
                Integer.parseInt(findViewById<EditText>(R.id.update_client_nif).text.toString())
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

    override fun onClient(listener: (Client) -> Unit) {
        val client = intent.getParcelableExtra<Client?>(CLIENT)
        if (client != null) {
            listener(client)
        } else {
            clientAccess.get(
                    UriUtils.getClientUri(resources, getId()).build().toString(),
                    Response.Listener{
                        listener(it)
                    }
            )
        }
    }
}