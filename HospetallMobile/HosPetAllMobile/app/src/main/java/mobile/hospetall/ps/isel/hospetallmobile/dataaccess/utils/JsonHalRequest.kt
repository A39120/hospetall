package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class  JsonHalRequest(
        private val token: String,
        method: Int,
        uri: String,
        request: JSONObject?,
        listener: Response.Listener<JSONObject>,
        error: Response.ErrorListener)
    : JsonObjectRequest(method, uri, request, listener, error) {


    override fun getHeaders(): MutableMap<String, String> {
        //val headers =  super.getHeaders()
        val headers =  HashMap<String, String>()
        if(method != Request.Method.GET)
            headers["Content-Type"] = "application/json"

        headers["Authorization"] = "Bearer $token"
        return headers
    }

}