
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


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = MobileDatabase.getInstance(context)
        clientDao = mDb.ClientDao()
        treatmentDao = mDb.TreatmentDao()
        consultationDao = mDb.ConsultationDao()
        petDao = mDb.PetDao()
        listDao = mDb.ListDao()
        eventDao = mDb.EventDao()
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
        val listList = List(50, {ListEntity("uri", "uri$it")})
        val list = List(50,  { Pet(
                "uri$it",
                it,
                "a",
                "a",
                "a",
                "a",
                it
        )})
        listDao.insertAll(listList)
        petDao.insertAll(list)
        val petListAll = petDao.getAll()
        Assert.assertFalse(petListAll.isEmpty())
        Assert.assertEquals(50, petListAll.size)

        val listDb = petDao.getList("uri")
        Assert.assertFalse(listDb.isEmpty())
        Assert.assertEquals(50, petListAll.size)

    }

}
