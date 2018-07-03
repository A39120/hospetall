package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for treatment table.
 */
@Dao
interface TreatmentDao : CollectionDao<Treatment>{

    /**
     * Gets a sing treatment with [id]
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun get(id: Int) : Treatment

    /**
     * Gets a single treatment with [uri]
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun get(uri: String) : Treatment

    /**
     * Inserts into treatment table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(treatment: Treatment)

    /**
     * Gets all treatments in database
     */
    @Query("SELECT * FROM ${Treatment.TABLE_NAME}")
    override fun getAll(): List<Treatment>

    @Query("SELECT ${Treatment.TABLE_NAME}.* FROM ${Treatment.TABLE_NAME} INNER JOIN ${ListEntity.TABLE_NAME} ON ${Treatment.TABLE_NAME}.${DatabaseColumns.URI} = ${ListEntity.TABLE_NAME}.${ListEntity.SINGLE} WHERE ${ListEntity.LIST} = :uri")
    override fun getList(uri: String) : List<Treatment>

    /**
     * Deletes treatment with a certain [id] from treatment table
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun deleteById(id: Int)

    /**
     * Deletes treatment with a certain [uri] from treatment table
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun deleteById(uri: String)

    /**
     * Clears table treatment
     */
    @Query("DELETE FROM ${Treatment.TABLE_NAME}")
    fun clear()

}