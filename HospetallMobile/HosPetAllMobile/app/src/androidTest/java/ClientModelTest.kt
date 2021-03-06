
import android.support.test.InstrumentationRegistry
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.activities.ChangeProfileActivity
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject
import org.junit.Test

class ClientModelTest {
    val json = "{\"id\":1,\"familyName\":\"Glenny\",\"givenName\":\"Zandra\",\"email\":\"zglenny0@auda.org.au\",\"telephone\":\"687-569-2069\",\"address\":\"96260 North Circle\",\"postalCode\":null,\"telephoneAlternative\":\"498-381-3900\",\"nif\":278705361,\"other\":\"ybCvZc8Vr96FaC4NXC0882FdFfnvjn4dihAtqY16fZYSjxGnIrGUuLP VG52QQUtxJwzP7Jlp26nILCK\",\"_links\":{\"self\":{\"href\":\"http://192.168.255.148/client/1\"},\"pets\":{\"href\":\"http://192.168.255.148/client/1/pets\"}}}"
    val uri = "http://192.168.255.148/client/1"

    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun getFromApi(){
        val json = JSONObject(json)
        val cli = getClientFromJson(json)

        Assert.assertEquals(uri, cli.uri)
        Assert.assertEquals(1, cli.id)
        Assert.assertEquals("zglenny0@auda.org.au", cli.email)
    }

    private fun getClientFromJson(json: JSONObject) =
            Client.parse(json)

    @Test
    fun profileActivityTest(){
        val json = JSONObject(json)
        val cli = getClientFromJson(json)
        ChangeProfileActivity.start(context, cli)
    }


}