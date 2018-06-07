package mobile.hospetall.ps.isel.hospetallmobile.utils

import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

interface OnPetListener {
    fun onPet(onPet: Response.Listener<Pet>)
}