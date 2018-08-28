package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.LoginDialog
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.security.OAuthRequest
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import org.json.JSONObject
import java.util.*

class MainActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/MAIN"

        fun start(context: Context) {
            val int = Intent(context, MainActivity::class.java)
            context.startActivity(int)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_activitiy)

        val pref = this.getSharedPreferences(SharedPrefKeys.SP_NAME, android.content.Context.MODE_PRIVATE)
        if(pref.getLong(SharedPrefKeys.EXPIRATION, -1) < Calendar.getInstance().timeInMillis) {
            if (pref.getString(SharedPrefKeys.USERNAME, null) != null) {
                login({ getId() }, {LoginDialog.start(this)})
            } else {
                LoginDialog.start(this)
            }
        }
    }

    fun login(successList: () -> Unit, errorList: () -> Unit) {
        val pref = this.getSharedPreferences(SharedPrefKeys.SP_NAME, android.content.Context.MODE_PRIVATE)
        RequestQueueSingleton.getInstance().requestQueue.add(
                OAuthRequest(pref, {
                    successList()
                }, Response.ErrorListener {
                    if (it.networkResponse.statusCode == 400) {
                        val json = JSONObject(String(it.networkResponse.data))
                        val error = json.optString("error_description")
                        if (error == "Bad credentials")
                            errorList()
                    }
                })
        )
    }

    fun getId(){
        val uri = UriUtils.getSelfClient().build().toString()

        RequestQueueSingleton.getInstance().get(
                this,
                uri,
                {
                    val id = it.getInt(DatabaseColumns.ID)
                    val pref = this@MainActivity.getSharedPreferences(SharedPrefKeys.SP_NAME, android.content.Context.MODE_PRIVATE)
                    pref.edit().apply{
                        putInt(DatabaseColumns.ID, id)
                        commit()
                    }
                },
                Response.ErrorListener { }
        )
    }
}
