package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.drm.DrmStore
import com.afollestad.materialdialogs.MaterialDialog
import com.dd.processbutton.iml.ActionProcessButton
import com.rengwuxian.materialedittext.MaterialEditText
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.MainActivity
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.PASSWORD
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.USERNAME

class LoginDialog {
    companion object {
        fun start(context: Context ) {
            val builder =
                    MaterialDialog.Builder(context)

            val md = builder
                    .title("Login")
                    .customView(R.layout.login_view, true)
                    .build()

            val button = md.view.findViewById<ActionProcessButton>(R.id.login)
            button.setOnClickListener {
                val user = md.view.findViewById<MaterialEditText>(R.id.username).text.toString()
                val password = md.view.findViewById<MaterialEditText>(R.id.password).text.toString()
                val sp = context.getSharedPreferences(SharedPrefKeys.SP_NAME, MODE_PRIVATE)

                sp.edit().apply {
                    putString(USERNAME, user)
                    putString(PASSWORD, password)
                    commit()
                }

                button.progress = 50
                (context as MainActivity).login({
                    button.progress = 100
                    md.dismiss()
                }, {
                    button.progress = -1
                })
            }
            md.show()

        }
    }
}
