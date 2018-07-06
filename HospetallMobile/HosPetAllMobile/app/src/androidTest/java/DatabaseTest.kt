
import android.support.test.InstrumentationRegistry
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.*
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import org.junit.After
import org.junit.Before
import org.junit.Test


class DatabaseTest {

    private lateinit var clientDao : ClientDao
    private lateinit var treatmentDao : TreatmentDao
    private lateinit var consultationDao : ConsultationDao
    private lateinit var petDao : PetDao
    private lateinit var listDao : ListDao
    private lateinit var eventDao: EventDao
    private lateinit var mDb : MobileDatabase

     val JSON = "{\"id\":50,\"name\":\"asfgfgdg, long-billed\",\"race\":\"Spurfowl, yellow-necked\",\"species\":\"Bettongia penicillata\",\"birthdate\":\"2000-08-06\",\"chip_number\":23,\"license_number\":84,\"_links\":{\"self\":{\"href\":\"http://192.168.255.148/pet/50\"},\"owner\":{\"href\":\"http://192.168.255.148/client/90\"},\"consultations\":{\"href\":\"http://192.168.255.148/pet/50/consultation\"}}}"
    val id = 50
    val name = "asfgfgdg, long-billed"
    val self = "http://192.168.255.148/pet/50"

    val petParcel = "pet"

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = MobileDatabase.getInstance(context)
        clientDao = mDb.clientDao()
        treatmentDao = mDb.treatmentDao()
        consultationDao = mDb.consultationDao()
        petDao = mDb.petDao()
        listDao = mDb.listDao()
        eventDao = mDb.eventDao()
    }

    @After
    fun closeDb() {
        mDb.clearAllTables()
        mDb.close()
    }

    @Test
    fun testClientDatabase(){
        val client = Client(
                "uri",
                1,
                "a",
                "a",
                "a",
                "1",
                "a",
                "a",
                1,
                "a",
                "a"
        )
        clientDao.insertOrUpdate(client)
        val newClient = clientDao.get(1)
        Assert.assertNotNull(newClient)
        Assert.assertEquals(client.uri, newClient.uri)
        clientDao.deleteById(1)
        val newNewClient = clientDao.get(1)
        Assert.assertNull(newNewClient)
    }

    @Test
    fun petTest() {
        val listList = List(50, { ListEntity("uri", "uri$it") })

        val list = List(50, {
            Pet(
                    "uri$it",
                    it,
                    "a",
                    "a",
                    "a",
                    "a",
                    it
            )
        })


    }
}
