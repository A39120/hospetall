package mobile.hospetall.ps.isel.hospetallmobile.utils

import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

interface OnConsultationListListener {
    fun onConsultationList(list: Response.Listener<List<Consultation>>)
}