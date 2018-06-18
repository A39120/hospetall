
import android.content.Intent
import android.support.test.InstrumentationRegistry
import com.android.volley.toolbox.Volley
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import org.json.JSONObject
import org.junit.Test

/**
 * Class that tests model at @{main/java/mobile/hospetall/ps/isel/hospetallmobile/models/Pet.kt}
 */
class PetModelTest {

    val JSON = "{\"id\":50,\"name\":\"Cockatoo, long-billed\",\"race\":\"Spurfowl, yellow-necked\",\"species\":\"Bettongia penicillata\",\"birthdate\":\"2000-08-06\",\"chip_number\":23,\"license_number\":84,\"_links\":{\"self\":{\"href\":\"http://192.168.255.148/pet/50\"},\"owner\":{\"href\":\"http://192.168.255.148/client/90\"},\"consultations\":{\"href\":\"http://192.168.255.148/pet/50/consultation\"}}}"
    val id = 50
    val name = "Cockatoo, long-billed"
    val self = "http://192.168.255.148/pet/50"

    val petParcel = "pet"

    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun getPetFromApi() {
        val obj = JSONObject(JSON)
        val pet = Pet.parse(obj)

        Assert.assertEquals(id, pet.id)
        Assert.assertEquals(name, pet.name)
        Assert.assertEquals(self, pet.uri)

    }

    @Test
    fun parcelPet(){
        val obj = JSONObject(JSON)
        val pet = Pet.parse(obj)

        val intent = Intent()
        intent.putExtra(petParcel, pet)

        val parceledPet = intent.getParcelableExtra<Pet>(petParcel)

        Assert.assertEquals(pet, parceledPet)
    }

    @Test
    fun petDatabaseTest(){
        val database = MobileDatabase.getInstance(context)
        Assert.assertNotNull(database)
        val access = PetAccess(Volley.newRequestQueue(context), database)
        Assert.assertNotNull(access)
        val obj = JSONObject(JSON)
        val pet = Pet.parse(obj)

        access.insertInDatabase(pet)
        val pet2= access.getFromDatabase(pet.uri)
        Assert.assertEquals(pet, pet2)
    }

}
