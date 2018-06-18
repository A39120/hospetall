package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

/**
 * Data access object for pet table.
 */
@Dao
interface PetDao {

    /**
     * Gets a sing pet with [id]
     */
    @Query("SELECT * FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun get(id: Int) : Pet

    /**
     * Gets a single pet with [uri]
     */
    @Query("SELECT * FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun get(uri: String) : Pet

    /**
     * Inserts into pet table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(pet: Pet)

    /**
     * Gets all pets in database
     */
    @Query("SELECT * FROM ${Pet.TABLE_NAME}")
    fun getAll(): List<Pet>

    /**
     * Deletes pet with a certain [id] from pet table
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.ID} = :id")
    fun deleteById(id: Int)

    /**
     * Deletes pet with a certain [uri] from pet table
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME} WHERE ${DatabaseColumns.URI} = :uri")
    fun deleteById(uri: String)

    /**
     * Clears table pet
     */
    @Query("DELETE FROM ${Pet.TABLE_NAME}")
    fun clear()


}