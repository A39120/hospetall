package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Client

@Database(entities = { Client::class, Pet::class, Consultation::class, Treatment::class }, version = 1)
abstract class AbstractDatabase : RoomDatabase() {


}