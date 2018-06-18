package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for treatment table.
 */
@Dao
interface TreatmentDao {

    /**
     * Gets a sing treatment with [id]
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun get(id: Int) : Treatment

    /**
     * Gets a single treatment with [uri]
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun get(uri: String) : Treatment

    /**
     * Inserts into treatment table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(treatment: Treatment)

    /**
     * Gets all treatments in database
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME}")
    fun getAll(): List<Treatment>

    /**
     * Deletes treatment with a certain [id] from treatment table
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun deleteById(id: Int)

    /**
     * Deletes treatment with a certain [uri] from treatment table
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun deleteById(uri: String)

    /**
     * Clears table treatment
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME}")
    fun clear()


}