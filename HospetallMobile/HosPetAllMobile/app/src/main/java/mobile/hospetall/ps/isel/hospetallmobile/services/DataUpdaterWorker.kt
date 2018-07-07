package mobile.hospetall.ps.isel.hospetallmobile.services

import android.app.Application
import android.content.Context
import androidx.work.Worker
import com.android.volley.Response
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ConsultationAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.PetAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.TreatmentAccess
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class DataUpdater(private val application: Application) : Worker() {

    companion object {
       const val NAME = "DATA_UPDATER"
       const val TAG = "HPA/WORK/UPDATER"
       fun start(context: Context){

       }
   }

    private val database by lazy { MobileDatabase.getInstance(this.applicationContext) }

    private val petAccess by lazy { PetAccess(application as HospetallApplication) }
    private val consultationAccess by lazy { ConsultationAccess(application as HospetallApplication) }
    private val treatmentAccess by lazy { TreatmentAccess(application as HospetallApplication)}

    override fun doWork(): Result {
        database.listDao().clear()

        val id = mobile.hospetall.ps.isel.hospetallmobile.utils.getId()
        val petUri = UriUtils.getClientsPetsUri(application.resources, id).build().toString()
        val consultationUri = UriUtils.getPetsConsultationsUri(application.resources, id).build().toString()
        val treatmentUri = UriUtils.getPetsTreatmentUri(application.resources, id).build().toString()

        database.petDao().clear()
        petAccess.getFromUri(petUri, Response.Listener {  })

        database.consultationDao().clear()
        consultationAccess.getFromUri(consultationUri, Response.Listener {  })

        database.treatmentDao().clear()
        treatmentAccess.getFromUri(treatmentUri, Response.Listener {  })

        return Result.SUCCESS
    }

}