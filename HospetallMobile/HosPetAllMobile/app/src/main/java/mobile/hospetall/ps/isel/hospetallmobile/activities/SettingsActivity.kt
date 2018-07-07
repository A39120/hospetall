package mobile.hospetall.ps.isel.hospetallmobile.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.widget.AdapterView
import android.widget.CheckBox
import mobile.hospetall.ps.isel.hospetallmobile.R
import mobile.hospetall.ps.isel.hospetallmobile.databinding.ActivitySettingsBinding
import mobile.hospetall.ps.isel.hospetallmobile.services.DataUpdaterWorker
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.DEFAULT_PERIOD_UNIT
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.DEFAULT_UPDATER
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.PERIOD_NUMBER
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.PERIOD_UNIT
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.SP_NAME
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.UPDATER_ACTIVE

class SettingsActivity : BaseActivity() {
    companion object {
        private const val TAG = "HPA/ACTIVITY/SETTINGS"

        fun start(context : Context) {
            val int = Intent(context, SettingsActivity::class.java)
            context.startActivity(int)
        }

    }

    private lateinit var mBinder : ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        sharedPreferences = getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

        setCheck()

        setPeriod()
    }

    private fun setCheck() {
        val check = mBinder.updater as CheckBox
        check.isChecked = sharedPreferences.getBoolean(UPDATER_ACTIVE, DEFAULT_UPDATER)

        check.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean(UPDATER_ACTIVE, isChecked)
            editor.apply()

            //Setting up the service DataUpdater Work
            if(isChecked) {
                DataUpdaterWorker.start(this, sharedPreferences)
            } else {
                DataUpdaterWorker.cancel()
            }
        }
    }


    private fun setPeriod(){
        //Setting Period Number value
        val periodText = mBinder.periodValue
        val periodNumberPref = sharedPreferences.getInt(PERIOD_NUMBER, DEFAULT_PERIOD_UNIT).toString()
        periodText.setText(periodNumberPref)
        periodText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == IME_ACTION_DONE) {
                Log.i(TAG, "Changed period number to ${v.text}")
                val editor = sharedPreferences.edit()
                try {
                    editor.putInt(PERIOD_NUMBER, Integer.valueOf(v.text.toString()))
                } finally {
                    editor.apply()
                }

                DataUpdaterWorker.cancel()
                DataUpdaterWorker.start(this, sharedPreferences)
            }
            true
        }
        val periodUnit = mBinder.periodConstants
        //periodUnit.adapter = PeriodSpinnerObject.getAdapter(this)
        periodUnit.setSelection(sharedPreferences.getInt(PERIOD_UNIT, DEFAULT_PERIOD_UNIT))
        periodUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) { }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val editor = sharedPreferences.edit()
                editor.putInt(PERIOD_UNIT, position)
                editor.apply()
                DataUpdaterWorker.cancel()
                DataUpdaterWorker.start(this@SettingsActivity, sharedPreferences)
            }
        }
    }

}