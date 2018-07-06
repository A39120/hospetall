package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.base

interface BaseDao<T> {

    fun get(id: Int) : T

    fun get(uri: String) : T

    fun insertOrUpdate(entity: T)

    fun deleteById(id: Int)

    fun deleteById(uri: String)
}