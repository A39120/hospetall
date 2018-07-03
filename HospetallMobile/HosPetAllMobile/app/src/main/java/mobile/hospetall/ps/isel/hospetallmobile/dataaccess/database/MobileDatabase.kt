package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.*
import mobile.hospetall.ps.isel.hospetallmobile.models.*

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
 *  [Event] - information on an event;
 */
@Database(entities = [Client::class, Pet::class, Consultation::class, Treatment::class, Event::class, ListEntity::class], version = 1)
abstract class MobileDatabase : RoomDatabase() {

    /**
     * Data access objects, interfaces that will communicate
     * with the database, each interface will communicate
     * with a table.
     */
    abstract fun ClientDao(): ClientDao
    abstract fun PetDao(): PetDao
    abstract fun ConsultationDao(): ConsultationDao
    abstract fun TreatmentDao(): TreatmentDao
    abstract fun EventDao(): EventDao
    abstract fun ListDao(): ListDao


    /**
     * Static components that serve to get the database or
     * information related to it.
     */
    companion object {
        private var database : MobileDatabase? = null
        private const val DATABASE_NAME = "main"

        /**
         * Gets the singleton instance of [MobileDatabase].
         *
         * @param context The context.
         * @return The singleton instance of [MobileDatabase].
         */
        @Synchronized
        fun getInstance(context: Context? = null): MobileDatabase{
            if(database == null) {
                if (context != null)
                    database = Room
                            .databaseBuilder(context.applicationContext, MobileDatabase::class.java, DATABASE_NAME)
                            .build()
                else
                    throw IllegalAccessException("Can't instantiate class from this context.")
            }

            return database as MobileDatabase
        }
    }
}