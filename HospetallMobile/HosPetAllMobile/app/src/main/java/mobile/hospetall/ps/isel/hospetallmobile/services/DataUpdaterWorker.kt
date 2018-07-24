package mobile.hospetall.ps.isel.hospetallmobile.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.WorkManager
import androidx.work.Worker
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.utils.ConnectionUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.DateUtils
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.DEFAULT_PERIOD_NUMBER
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.DEFAULT_PERIOD_UNIT
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.PERIOD_NUMBER
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.SharedPrefKeys.PERIOD_UNIT
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class DataUpdaterWorker : Worker() {

    companion object {
       private const val NAME = "DATA_UPDATER"
       private const val TAG = "HPA/WORK/UPDATER"

        /**
         * Setup the Periodic Updater.
         *
         * Minimum periodic time is: 15 minutes
         */
        fun start(context: Context, sharedPreferences: SharedPreferences){
            val duration = sharedPreferences.getInt(PERIOD_UNIT, DEFAULT_PERIOD_UNIT)
            val spNumber = sharedPreferences.getInt(PERIOD_NUMBER, DEFAULT_PERIOD_NUMBER)

            val number = DateUtils.getPeriod(duration, spNumber)
            val unit = DateUtils.getPeriodUnit(duration)

            Log.i(TAG, "Setting up repetitive work service: $number $unit")
            val periodWork =
                androidx.work.PeriodicWorkRequestBuilder<DataUpdaterWorker>(number, unit)
                        .addTag(NAME)
            val req = periodWork.build()
            WorkManager.getInstance()?.enqueue(req)
       }

        /**
         * Cancels this work
         */
        fun cancel() {
            Log.i(TAG, "Cancelling data updater.")
            WorkManager.getInstance()?.cancelAllWorkByTag(NAME)
        }
   }

    private val database by lazy { MobileDatabase.getInstance(this.applicationContext) }

    private val petAccess by lazy { PetAccess() }
    private val consultationAccess by lazy { ConsultationAccess() }
    private val treatmentAccess by lazy { TreatmentAccess()}

    override fun doWork(): Result {
        Log.i(TAG, "Updating data.")

        if(!ConnectionUtils.isConnectionAvailable(applicationContext)){
            Log.w(TAG, "Connection has not the available conditions to update the data")
            return Result.FAILURE
        }

        database.listDao().clear()

        val id = mobile.hospetall.ps.isel.hospetallmobile.utils.getId()
        val petUri = UriUtils.getClientsPetsUri(id).build().toString()
        val consultationUri = UriUtils.getPetsConsultationsUri(id).build().toString()
        val treatmentUri = UriUtils.getPetsTreatmentUri(id).build().toString()

        database.petDao().clear()
        petAccess.getCollectionFromUri(petUri, Response.Listener {  })

        database.consultationDao().clear()
        consultationAccess.getCollectionFromUri(consultationUri, Response.Listener {  })

        database.treatmentDao().clear()
        treatmentAccess.getCollectionFromUri(treatmentUri, Response.Listener {  })

        return Result.SUCCESS
    }

}