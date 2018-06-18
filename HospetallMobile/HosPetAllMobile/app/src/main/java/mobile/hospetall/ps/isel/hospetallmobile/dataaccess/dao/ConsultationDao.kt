package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for pet table.
 */
@Dao
interface ConsultationDao {

    /**
     * Gets a sing consultation with [id]
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun get(id: Int) : Consultation

    /**
     * Gets a single consultation with [uri]
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun get(uri: String) : Consultation

    /**
     * Inserts into consultation table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(consultation: Consultation)

    /**
     * Gets all consultations in database
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME}")
    fun getAll(): List<Consultation>

    /**
     * Deletes consultation with a certain [id] from consultation table
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun deleteById(id: Int)

    /**
     * Deletes consultation with a certain [uri] from consultation table
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun deleteById(uri: String)

    /**
     * Clears table consultation
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME}")
    fun clear()


}