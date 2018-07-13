package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base

import android.arch.lifecycle.LiveData

interface BaseDao<T> {

    fun get(id: Int) : LiveData<T>

    fun get(uri: String) : LiveData<T>

    fun insertOrUpdate(entity: T)

    fun deleteById(id: Int)

    fun deleteById(uri: String)
}