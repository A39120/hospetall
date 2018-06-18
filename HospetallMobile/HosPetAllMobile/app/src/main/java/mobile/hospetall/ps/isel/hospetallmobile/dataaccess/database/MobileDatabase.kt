package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ClientDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ConsultationDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.PetDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.TreatmentDao
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment


/**
 * The database for the following classes:
 *  [Client] - information pertaining the client, should only have
 *  one row for the user;
 *  [Pet] - information pertaining the pets associated with the
 *  client;
 *  [Consultation] - information about the consultation for the
 *  pets that belong to the current client;
 *  [Treatment] - information about the treatment for the pets
 *  that belong to the current client;
 */
@Database(entities = [Client::class, Pet::class, Consultation::class, Treatment::class], version = 1)
abstract class MobileDatabase : RoomDatabase() {

    /**
     * Data access objects, interfaces that will communicate
     * with the database, each interface will communicate
     * with a table.
     */
    abstract val clientDao: ClientDao
    abstract val petDao : PetDao
    abstract val consultationDao : ConsultationDao
    abstract val treatmentDao: TreatmentDao


    /**
     * Static components that serve to get the database or
     * information related to it.
     */
    companion object {
        private var database : MobileDatabase? = null
        const val DATABASE_NAME = "main"

        /**
         * Gets the singleton instance of [MobileDatabase].
         *
         * @param context The context.
         * @return The singleton instance of [MobileDatabase].
         */
        @Synchronized
        fun getInstance(context: Context): MobileDatabase{
            if(database == null)
                    database = Room
                            .databaseBuilder(context.applicationContext, MobileDatabase::class.java, DATABASE_NAME)
                            .build()

            return database as MobileDatabase
        }
    }
}