package mobile.hospetall.ps.isel.hospetallmobile.activities.fragments

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

class AlertDialog {
    companion object {
        fun start(context: Context, message : Int) {
            val builder =
                 MaterialDialog.Builder(context)

            builder.title("Warning:")
                    .content(message)
                    .onNeutral { dialog, _ -> dialog.cancel() }
                    .neutralText(android.R.string.ok)
                    .show()
        }
    }
}
