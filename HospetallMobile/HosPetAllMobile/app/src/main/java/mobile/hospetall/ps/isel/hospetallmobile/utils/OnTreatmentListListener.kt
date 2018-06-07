package mobile.hospetall.ps.isel.hospetallmobile.utils

import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

interface OnTreatmentListListener {
    fun onTreatmentList(onList: Response.Listener<List<Treatment>>)
}