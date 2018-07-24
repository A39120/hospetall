
import android.content.Intent
import android.support.test.InstrumentationRegistry
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.utils.RequestQueueSingleton
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import org.json.JSONObject
import org.junit.Test

class ConsultationModelTest {
    val JSON = "{\"id\":1,\"caseHistory\":\"Organized national forecast\",\"diagnosis\":\"Persistent dynamic task-force\",\"treatment\":\"Synchronised mobile extranet\",\"observations\":\"Ergonomic 6th generation solution\",\"date\":\"2015-12-19\",\"weight\":63.0,\"temperature\":72.55,\"heartRhythm\":50.96,\"_links\":{\"self\":{\"href\":\"http://192.168.255.148/consultation/1\"},\"pet\":{\"href\":\"http://192.168.255.148/pet/34\"},\"veterinarian\":{\"href\":\"http://192.168.255.148/veterinarian/37\"}}}"

    val id = 1
    val caseHistory = "Organized national forecast"
    val parcel = "aaaaa"
    private val context = InstrumentationRegistry.getTargetContext()
    private val requestQueueSingleton = RequestQueueSingleton.getInstance(context)

    @Test
    fun getPetFromApi() {
        val obj = JSONObject(JSON)
        val cons = Consultation.parse(obj)

        Assert.assertEquals(id, cons.id)
        Assert.assertEquals(caseHistory, cons.caseHistory)

    }

    @Test
    fun parcelTest(){
        val obj = JSONObject(JSON)
        val cons = Consultation.parse(obj)

        val intent = Intent()
        intent.putExtra(parcel, cons)

        val parceled= intent.getParcelableExtra<Consultation>(parcel)

        Assert.assertEquals(cons, parceled)
    }


}