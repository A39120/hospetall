package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityAddEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils
import java.util.*

class AddEventActivity : BaseActivity(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener
{

    companion object {
        const val TAG = "HPA/ACTIVITY/ADD_EVENT"

        fun start(context: Context){
            val int = Intent(context, AddEventActivity::class.java)
            context.startActivity(int)
        }
    }

    private val timePickerDialog by lazy { getTimePicker() }
    private val datePickerDialog by lazy { getDatePicker() }
    private val calendar by lazy { Calendar.getInstance() }
    private val dateFormat  by lazy { getDateFormat(applicationContext) }
    private val timeFormat by lazy { getTimeFormat(applicationContext) }
    private val petAccess : PetAccess by lazy { PetAccess() }
    private lateinit var pets :  List<Pet>

    private lateinit var mBinder: ActivityAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_add_event)
        mBinder.time = timeFormat.format(calendar)
        mBinder.date = dateFormat.format(calendar)
        mBinder.newEventSetDate.setOnClickListener {
            datePickerDialog.show()
        }

        mBinder.newEventSetTime.setOnClickListener {
            timePickerDialog.show()
        }

        petAccess.getList(
                UriUtils.getClientsPetsUri(getId()).build().toString(),
                Response.Listener {
                    pets = it.sortedBy { it.id }
                    val names = pets.map { it.name }
                    val dataAdapter = ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, names)
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mBinder.petList.adapter = dataAdapter
                },
                Response.ErrorListener {  }
        )

        mBinder.newEventAddButton.setOnClickListener{ addEvent() }
        mBinder.executePendingBindings()
    }

    private fun addEvent(){

        /*
        val title: String,
        val message: String?,
        val pet: Int?,
        val period: Long = -1,
        val timedate: Long,
        val appointed: Boolean = false,
        val type: Int = EventType.USER
         */

        val position = mBinder.petList.selectedItemPosition
        val selectedPet = if(position == AdapterView.INVALID_POSITION){
            null
        } else
            pets[position]

        /*
       val period =

        val event = Event(
                mBinder.newEventTitle.text.toString(),
                mBinder.newEventSummary.text.toString(),
                selectedPet?.id,


        )
        */
    }

    /**
     * After picking a date from DatePicker Dialog this will
     * trigger.
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "Date chosen from DatePicker for $dayOfMonth/$month/$year")
        setDate(year, month, dayOfMonth)
        mBinder.date = dateFormat.format(calendar)
        mBinder.executePendingBindings()
    }

    /**
     * Sets a date and returns this date as [Calendar] instance.
     * @return [Calendar] instance
     */
    private fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "Setting local calendar date for $dayOfMonth/$month/$year")
        calendar.set(year, month, dayOfMonth)
        datePickerDialog.updateDate(year, month, dayOfMonth)
    }

    /**
     * When Time picker sets a date this will trigger.
     */
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
    }

    /**
     * [DatePickerDialog] initiator
     */
    private fun getDatePicker() : DatePickerDialog {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return DatePickerDialog(this.baseContext, this , day, month, year)
    }

    /**
     * [TimePickerDialog] initiator
     */
    private fun getTimePicker() : TimePickerDialog {
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val is24hour =DateFormat.is24HourFormat(applicationContext)
        return TimePickerDialog(this.baseContext, this, hour, minute, is24hour)
    }

}