package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for [Client] table.
 */
@Dao
interface ClientDao {

    /**
     * Gets a client with [id]
     */
    @Query("SELECT * FROM ${Client.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun get(id: Int) : Client

    /**
     * Gets the client with [uri]
     */
    @Query("SELECT * FROM ${Client.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun get(uri: String) : Client

    /**
     * Inserts/updates client in the client table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(client: Client)

    /**
     * Deletes client info with a certain [id] from client table
     */
    @Query("DELETE FROM ${Client.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun deleteById(id: Int)

}