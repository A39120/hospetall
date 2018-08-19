import android.content.Context.MODE_PRIVATE
import android.support.test.InstrumentationRegistry
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.security.OAuthRequest
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import org.junit.Test
import java.util.*

class OAuthRequestTest{

    private val SP_TEST = "sp_test"
    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun requestTest(){
        val prefs = context.getSharedPreferences(SP_TEST, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(SharedPrefKeys.USERNAME, "abownassir@hhs.gov")
        editor.putString(SharedPrefKeys.PASSWORD, "password")
        editor.commit()

        var token : String? = null

        val request = OAuthRequest(
                prefs,
                {token = it.token},
                Response.ErrorListener{
                    Assert.fail("Error: ${it.printStackTrace()}")
                }
        )

        val vol = Volley.newRequestQueue(context)
        val req = vol.add(request)

        while(!req.hasHadResponseDelivered());

        Assert.assertNotNull(prefs.getString(SharedPrefKeys.TOKEN, null))
        Assert.assertFalse(prefs.getString(SharedPrefKeys.TOKEN, "") == "")
        Assert.assertNotSame(prefs.getLong(SharedPrefKeys.EXPIRATION, 0), 0)
    }

}