package mobile.hospetall.ps.isel.hospetallmobile.services

import android.app.Application
import android.content.Context
import androidx.work.Worker
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

    private val petAccess by lazy { PetAccess() }
    private val consultationAccess by lazy { ConsultationAccess() }
    private val treatmentAccess by lazy { TreatmentAccess()}

    override fun doWork(): Result {
        database.ListDao().clear()

        val id = mobile.hospetall.ps.isel.hospetallmobile.utils.getId()
        val petUri = UriUtils.getClientsPetsUri(application.resources, id)
        val consultationUri = UriUtils.getPetsConsultationsUri(application.resources, id)
        val treatmentUri = UriUtils.getPetsTreatmentUri(application.resources, id)

        petAccess.update(petUri.toString())
        consultationAccess.update(consultationUri.toString())
        treatmentAccess.update(treatmentUri.toString())

        return Result.SUCCESS
    }

}