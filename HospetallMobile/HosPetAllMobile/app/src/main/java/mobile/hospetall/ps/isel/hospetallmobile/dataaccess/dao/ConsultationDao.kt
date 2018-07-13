package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for pet table.
 */
@Dao
interface ConsultationDao : CollectionDao<Consultation> {

    /**
     * Gets a sing consultation with [id]
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun get(id: Int) : LiveData<Consultation>

    /**
     * Gets a single consultation with [uri]
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun get(uri: String) : LiveData<Consultation>

    /**
     * Inserts into consultation table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(consultation: Consultation)

    /**
     * Gets all consultations in database
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME}")
    override fun getAll(): LiveData<List<Consultation>>

    @Query("SELECT ${Consultation.TABLE_NAME}.* FROM ${Consultation.TABLE_NAME} INNER JOIN ${ListEntity.TABLE_NAME} ON ${Consultation.TABLE_NAME}.${DatabaseColumns.URI} = ${ListEntity.TABLE_NAME}.${ListEntity.SINGLE} WHERE ${ListEntity.LIST} = :uri")
    override fun getList(uri: String) : LiveData<List<Consultation>>

    /**
     * Deletes consultation with a certain [id] from consultation table
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun deleteById(id: Int)

    /**
     * Deletes consultation with a certain [uri] from consultation table
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun deleteById(uri: String)

    /**
     * Clears table consultation
     */
    @Query("DELETE FROM ${Consultation.TABLE_NAME}")
    fun clear()

}