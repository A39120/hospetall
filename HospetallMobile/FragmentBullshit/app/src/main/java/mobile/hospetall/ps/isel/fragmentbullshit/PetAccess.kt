package mobile.hospetall.ps.isel.fragmentbullshit

import android.net.Uri
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class PetAccess(val queue: RequestQueue) {

    val uri = Uri.Builder().authority("http")
            .authority("10.10.1.109")
            .appendPath("pet")
            .appendPath("1")

    interface PetListener {
        fun onPetListener(pet : String)
    }



}