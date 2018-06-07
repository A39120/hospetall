package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.parsePet
import org.json.JSONObject

class PetAccess(queue:RequestQueue)
    : AbstractAccess<Pet>(queue) {
    companion object {
        private const val TAG = "PET/ACCESS"
    }

    override fun parse(json: JSONObject) = parsePet(json)

}
