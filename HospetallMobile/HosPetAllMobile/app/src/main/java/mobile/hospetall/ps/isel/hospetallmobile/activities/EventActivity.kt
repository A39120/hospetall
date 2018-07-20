package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.EventViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ScheduleAccess
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityEventBinding
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.services.OneTimeNotificationWorker
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import java.util.*


class EventActivity : BaseActivity() {
    companion object {
        const val TAG = "HPA/ACTIVITY/EVENT"
        const val ID = "event_id"

        fun start(context: Context, id : Int){
            val int = Intent(context, EventActivity::class.java)
            int.putExtra(ID, id)
            context.startActivity(int)
        }
    }

    private lateinit var mBinder : ActivityEventBinding
    private lateinit var viewModel : EventViewModel
    private val dateFormat  by lazy { getDateFormat(applicationContext) }
    private val timeFormat by lazy { getTimeFormat(applicationContext) }
    private val scheduleRepo by lazy { ScheduleAccess() }

    /**
     * Creates event activity instantiating Binder and ViewModel
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_event)

        val id = intent.getIntExtra(ID, 0)
        if(id==0)  finish()

        Log.i(TAG, "Starting event $id")
        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.init(id)

        viewModel.getEvent()?.observe(this, Observer {
            it?.run {
                Log.i(TAG, "Binding event $id to view.")
                mBinder.event = it
                val date = Date(it.timedate)
                mBinder.date = dateFormat.format(date)
                mBinder.time = timeFormat.format(date)
                mBinder.type = Event.CREATOR.EventType.getType(it.type)
                mBinder.period = DateUtils.getPeriodString(it.periodUnit, it.period)
                if(it.type > 1)
                    mBinder.appointed = if(it.appointed) "Yes" else "No"

                mBinder.eventModify.setOnClickListener { AddEventActivity.start(this@EventActivity, this.id) }
                mBinder.eventCancel.setOnClickListener {
                    scheduleRepo.delete(this.id, { this@EventActivity.finish()})
                    OneTimeNotificationWorker.cancelWork(this.id)
                }
                mBinder.executePendingBindings()
            }
        })

        viewModel.getPet()?.observe(this, Observer {
            it?.run{
                Log.i(TAG, "Binding pet name to View.")
                mBinder.petName = it.name
                mBinder.executePendingBindings()
            }
        })

    }

}