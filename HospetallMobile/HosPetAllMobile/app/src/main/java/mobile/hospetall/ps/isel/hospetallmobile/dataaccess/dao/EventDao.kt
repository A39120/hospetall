package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.models.Event
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

@Dao
interface EventDao {


    /**
     * Gets a client with [id]
     */
    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun get(id: Int) : LiveData<Event>

    /**
     * Gets all the events
     */
    @Query("SELECT * FROM ${Event.TABLE_NAME}")
    fun getAll(): LiveData<List<Event>>

    /**
     * Gets events before date
     * @param date: Date in milliseconds
     */
    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.TIME} < :time AND ${Event.PERIOD} <= 0 ORDER BY ${Event.TIME} DESC")
    fun getBefore(time: Long): LiveData<List<Event>>

    /**
     * Gets events for after date
     * @param date: Date in milliseconds
     */
    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.TIME} >= :time AND ${Event.PERIOD} <= 0 ORDER BY ${Event.TIME}")
    fun getAfter(time: Long): LiveData<List<Event>>

    /**
     * Inserts/updates client in the client table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(client: Event) : Long

    /**
     *
     */
    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.PERIOD} > 0 ORDER BY ${Event.PERIOD}")
    fun getPeriodic(): LiveData<List<Event>>

    /**
     * Deletes client info with a certain [id] from client table
     */
    @Query("DELETE FROM ${Event.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun deleteById(id: Int)
}