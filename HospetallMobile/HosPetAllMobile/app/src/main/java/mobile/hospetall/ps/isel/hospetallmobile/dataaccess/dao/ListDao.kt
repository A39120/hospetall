package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: ListEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<ListEntity>)

    @Query("SELECT * FROM ${ListEntity.TABLE_NAME}")
    fun getAll(): List<ListEntity>

    @Query("SELECT * FROM ${ListEntity.TABLE_NAME} WHERE ${ListEntity.LIST} LIKE :uri")
    fun get(uri: String) : List<ListEntity>

    @Query("DELETE FROM ${ListEntity.TABLE_NAME} WHERE ${ListEntity.LIST} = :uri")
    fun delete(uri: String): Int

    @Query("DELETE FROM ${ListEntity.TABLE_NAME}")
    fun clear()
}