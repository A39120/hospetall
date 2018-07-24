
import android.support.test.InstrumentationRegistry
import com.android.volley.Response
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.AbstractAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ClientDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test



class AccessTest {
    private lateinit var db : MobileDatabase
    private lateinit var clientDao : ClientDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = MobileDatabase.getInstance(context)
        clientDao = db.clientDao()

    }

    @After
    fun clear(){
        db.clearAllTables()
        db.close()
    }

    @Test
    fun clientAccessTest(){
        val json = "{\"id\":1,\"familyName\":\"Glenny\",\"givenName\":\"Zandra\",\"email\":\"zglenny0@auda.org.au\",\"telephone\":\"687-569-2069\",\"address\":\"96260 North Circle\",\"postalCode\":null,\"telephoneAlternative\":\"498-381-3900\",\"nif\":278705361,\"other\":\"ybCvZc8Vr96FaC4NXC0882FdFfnvjn4dihAtqY16fZYSjxGnIrGUuLP VG52QQUtxJwzP7Jlp26nILCK\",\"_links\":{\"self\":{\"href\":\"http://192.168.255.148/client/1\"},\"pets\":{\"href\":\"http://192.168.255.148/client/1/pets\"}}}"
        val client = Client.parse(JSONObject(json))

        AbstractAccess.Companion.InsertAsyncTask(clientDao).execute(client)
        Thread.sleep(1000)
        AbstractAccess.Companion.GetAsyncTask(clientDao,
                Response.Listener {
                    Assert.assertNotNull(it)
                    Assert.assertEquals(client, it)
                }).execute(client.uri)

    }

}