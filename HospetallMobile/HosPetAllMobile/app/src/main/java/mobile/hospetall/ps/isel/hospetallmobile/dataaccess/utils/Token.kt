package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils

import org.json.JSONObject

data class Token(
        private val accessToken : String,
        private val expirationDate: Long
) {
    companion object {

        private const val ACCESS_TOKEN = "access_token"
        private const val EXPIRATION = "expires_in"

    }
}

//{"access_token":"33022c96-99fc-4823-a8f1-e852fad8ad25","token_type":"bearer","expires_in":2469,"scope":"read write trusted"}