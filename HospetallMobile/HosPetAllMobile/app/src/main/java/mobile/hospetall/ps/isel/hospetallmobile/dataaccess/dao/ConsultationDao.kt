package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.*
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
    override fun get(id: Int) : Consultation

    /**
     * Gets a single consultation with [uri]
     */
    @Query("SELECT * FROM ${Consultation.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun get(uri: String) : Consultation

    /**
     * Inserts into consultation table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(consultation: Consultation)

    /**
     * Gets all consultations in database
     */
    @Transaction
    @Query("SELECT * FROM ${Consultation.TABLE_NAME}")
    override fun getAll(): List<Consultation>

    @Transaction
    @Query("SELECT ${Consultation.TABLE_NAME}.* FROM ${Consultation.TABLE_NAME} INNER JOIN ${ListEntity.TABLE_NAME} ON ${Consultation.TABLE_NAME}.${DatabaseColumns.URI} = ${ListEntity.TABLE_NAME}.${ListEntity.SINGLE} WHERE ${ListEntity.LIST} = :uri")
    override fun getList(uri: String) : List<Consultation>

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