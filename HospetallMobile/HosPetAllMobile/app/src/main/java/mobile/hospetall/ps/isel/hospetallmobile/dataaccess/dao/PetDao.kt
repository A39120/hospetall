package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base.CollectionDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for pet table.
 */
@Dao
interface PetDao : CollectionDao<Pet> {

    /**
     * Gets a sing pet with [id]
     */
    @Query("SELECT * FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun get(id: Int) : LiveData<Pet>

    /**
     * Gets a single pet with [uri]
     */
    @Query("SELECT * FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun get(uri: String) : LiveData<Pet>

    /**
     * Inserts into pet table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(entity: Pet)

    /**
     * Gets all pets in database
     */
    @Transaction
    @Query("SELECT * FROM ${Pet.TABLE_NAME}")
    override fun getAll(): LiveData<List<Pet>>


    @Transaction
    @Query("SELECT ${Pet.TABLE_NAME}.* FROM ${Pet.TABLE_NAME} INNER JOIN ${ListEntity.TABLE_NAME} ON ${Pet.TABLE_NAME}.${DatabaseColumns.URI} = ${ListEntity.TABLE_NAME}.${ListEntity.SINGLE} WHERE ${ListEntity.TABLE_NAME}.${ListEntity.LIST} LIKE :uri")
    override fun getList(uri: String) : LiveData<List<Pet>>

    /**
     * Deletes pet with a certain [id] from pet table
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    override fun deleteById(id: Int)

    /**
     * Deletes pet with a certain [uri] from pet table
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    override fun deleteById(uri: String)

    /**
     * Clears table pet
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME}")
    fun clear()


}