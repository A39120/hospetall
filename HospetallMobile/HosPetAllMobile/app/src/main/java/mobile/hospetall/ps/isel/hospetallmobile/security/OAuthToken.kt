package mobile.hospetall.ps.isel.hospetallmobile.security

import org.json.JSONObject

data class OAuthToken( val token: String, val expiresIn : Long ) {
    companion object {

        private const val TOKEN = "access_token"
        private const val EXPIRATION = "expires_in"

        fun parse(json : JSONObject, requestTime : Long) =
            OAuthToken( json.getString(TOKEN), (json.getLong(EXPIRATION) * 1000 + requestTime) )

    }
}