package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.fragments.AlertDialog
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.PetListViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityAddEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.services.OneTimeNotificationWorker
import mobile.hospetall.ps.isel.hospetallmobile.utils.getId
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

    private lateinit var viewModel : PetListViewModel
    private val timePickerDialog by lazy { getTimePicker() }
    private val datePickerDialog by lazy { getDatePicker() }
    private val calendar by lazy { Calendar.getInstance() }
    private val dateFormat  by lazy { getDateFormat(applicationContext) }
    private val timeFormat by lazy { getTimeFormat(applicationContext) }

    private lateinit var mBinder: ActivityAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Setting up add event activity.")

        //Binding data to layout
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_add_event)

        //Creating view model
        setupViewModel()

        //Setting up the current time and date for the current view
        mBinder.time = timeFormat.format(calendar.timeInMillis)
        mBinder.date = dateFormat.format(calendar.timeInMillis)

        mBinder.newEventChangeDateButton.setOnClickListener { datePickerDialog.show() }
        mBinder.newEventChangeTimeButton.setOnClickListener{ timePickerDialog.show() }

        setShowHide()
        mBinder.newEventAddButton.setOnClickListener{ addEvent() }
        mBinder.executePendingBindings()
    }

    private var petListAdapter : ArrayAdapter<String>? = null

    /**
     * Sets up [PetListViewModel] for the spinner and
     * it's observers.
     */
    private fun setupViewModel() {
        Log.i(TAG, "Setting up view model.")
        val id = getId()

        viewModel = ViewModelProviders.of(this).get(PetListViewModel::class.java)
        viewModel.init(id)
        viewModel.getPetList()?.observe(this, android.arch.lifecycle.Observer {
            Log.i(TAG, "Pet data has changed, calling observer.")
            it?.run {
                val names = this.sortedBy { it.id }
                            .map { it.name }

                if(petListAdapter == null) {
                    val listWithEmpty = mutableListOf<String>()
                    listWithEmpty.addAll(names)
                    listWithEmpty.add(0, "")
                    petListAdapter = ArrayAdapter(this@AddEventActivity,  android.R.layout.simple_spinner_item, listWithEmpty)
                    petListAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mBinder.petList.adapter = petListAdapter
                } else {
                    petListAdapter!!.clear()
                    petListAdapter!!.addAll(names)
                    petListAdapter!!.notifyDataSetChanged()
                }
                mBinder.executePendingBindings()
            }
        })
    }


    private fun setShowHide(){
        mBinder.newEventPeriodicChecker.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                mBinder.newEventPeriod?.periodValueLayout?.visibility = View.VISIBLE
            else
                mBinder.newEventPeriod?.periodValueLayout?.visibility = View.GONE

        }

        mBinder.typeValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position > 1) {
                    mBinder.newEventAppointment.visibility = View.VISIBLE
                } else
                    mBinder.newEventAppointment.visibility = View.GONE
            }

        }
    }

    private fun addEvent(){
        val event = getEventFromInfo()
        if(event == null) return
        ScheduleAccess().put(event, {
            OneTimeNotificationWorker.setUpWork(event, it)
            finish()
        })
    }


    /**
     * Gets [Event] from the input information in the layout.
     */
    private fun getEventFromInfo() : Event?{
        //Get message value
        val title = mBinder.newEventTitle.text.toString()

        if(title == "") {
            AlertDialog.start(this@AddEventActivity, R.string.add_event_error_no_title)
            return null
        }

        //Gets message contents
        val summary = mBinder.newEventSummary.text.toString()

        //Getting pet
        val petSelected = mBinder.petList.selectedItemPosition - 1
        val pet = if(petSelected >= 0){
            val list = viewModel
                    .getPetList()?.value!!
                    .sortedBy { it.id }
            if(list.isNotEmpty())
                list[petSelected]
            else null
        } else null

        var period = -1
        var unit = 0
        if(mBinder.newEventPeriodicChecker.isChecked) {
            mBinder.newEventPeriod?.apply {
                unit = periodConstants.selectedItemPosition
                val periodText  = periodValue.text.toString()
                if(periodText == ""){
                    AlertDialog.start(this@AddEventActivity, R.string.add_event_error_no_periodic_specification)
                    return null
                }

                period = Integer.parseInt(periodValue.text?.toString()?:"-1")
            }
        }

        val appointment = mBinder.newEventAppointmentChecker.isChecked
        val type = mBinder.typeValue.selectedItemPosition

        val timedate = calendar.timeInMillis

        return Event(
                title = title,
                message = summary,
                pet = pet?.id,
                period = period,
                periodUnit = unit,
                timedate = timedate,
                appointed = appointment,
                type = type
        )
    }

    /**
     * After picking a date from DatePicker Dialog this will
     * trigger.
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.i(TAG, "Date chosen from DatePicker for $dayOfMonth/$month/$year")
        setDate(year, month, dayOfMonth)

        mBinder.date = dateFormat.format(calendar.timeInMillis)
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
        mBinder.time = timeFormat.format(calendar.timeInMillis)
        mBinder.executePendingBindings()
    }

    /**
     * [DatePickerDialog] initiator
     */
    private fun getDatePicker() : DatePickerDialog {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        return DatePickerDialog(this, this , year, month, day)
    }

    /**
     * [TimePickerDialog] initiator
     */
    private fun getTimePicker() : TimePickerDialog {
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val is24hour =DateFormat.is24HourFormat(applicationContext)
        return TimePickerDialog(this, this, hour, minute, is24hour)
    }

}