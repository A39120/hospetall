package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel.EventViewModel
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivityEventBinding
import java.util.*


class EventActivity : BaseActivity() {
    companion object {
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

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_event)

        val id = intent.getIntExtra(ID, 0)
        if(id==0)  return

        viewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        viewModel.init(id)

        viewModel.getEvent()?.observe(this, Observer {
            it?.run {
                mBinder.event = it
                val date = Date(it.timedate)
                mBinder.date = dateFormat.format(date)
                mBinder.time = timeFormat.format(date)
                mBinder.executePendingBindings()
            }
        })

        viewModel.getPet()?.observe(this, Observer {
            it?.run{
                mBinder.petName = it.name
                mBinder.executePendingBindings()
            }
        })
    }

}