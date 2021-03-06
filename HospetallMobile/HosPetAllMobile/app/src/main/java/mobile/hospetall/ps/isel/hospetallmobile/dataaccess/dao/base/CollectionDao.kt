package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Transaction

interface CollectionDao<T> : BaseDao<T>{

    fun getAll() : LiveData<List<T>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list : List<T>)

    fun getList(uri: String) : LiveData<List<T>>

}