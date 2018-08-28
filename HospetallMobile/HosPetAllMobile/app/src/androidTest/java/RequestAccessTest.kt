import android.content.Context.MODE_PRIVATE
import android.support.test.InstrumentationRegistry
import com.android.volley.Request
import com.android.volley.Response
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.JsonHalRequest
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.security.OAuthRequest
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import org.json.JSONObject
import org.junit.Test

class RequestAccessTest{

    private val context = InstrumentationRegistry.getTargetContext()
    private val rq = RequestQueueSingleton.getInstance(context)
    private val SP_TEST = "sp_test"


    @Test
    fun AccessTest(){
        val prefs = context.getSharedPreferences(SP_TEST, MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.putString(SharedPrefKeys.USERNAME, "abownassir@hhs.gov")
        editor.putString(SharedPrefKeys.PASSWORD, "password")
        editor.commit()

        var obj : JSONObject? = null
        rq.requestQueue.add(OAuthRequest(
                prefs,
                {
                    println(it.token)
                    rq.requestQueue.add(
                            JsonHalRequest(
                                it.token,
                                Request.Method.GET,
                                UriUtils.getSelfClient().build().toString(),
                                null,
                                    Response.Listener {
                                        obj = it
                                    },
                                    Response.ErrorListener {
                                        Assert.fail()
                                    })
                    )
                },
                Response.ErrorListener {
                    Assert.fail()
                })
        )

        while (obj == null);

        Assert.assertNotNull(obj?.optString("givenName"))
        Assert.assertNotNull(obj?.optString("familyName"))
    }

}