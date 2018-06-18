import android.support.test.InstrumentationRegistry
import junit.framework.Assert
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import org.junit.Test



class DatabaseTest {

    private val context = InstrumentationRegistry.getTargetContext()

    @Test
    fun databaseTest() {
        val database = MobileDatabase.getInstance(context)
        Assert.assertNotNull(database)
    }

}