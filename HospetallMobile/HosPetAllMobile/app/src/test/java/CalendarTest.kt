
import org.junit.Test
import java.text.DateFormat.getDateInstance
import java.util.*

class CalendarTest {

    @Test
    fun calendarTest(){
        val dateFormat = getDateInstance()
        println(dateFormat.format(Calendar.getInstance()))

    }
}