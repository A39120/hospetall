package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity.Companion.LIST
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity.Companion.SINGLE
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.ListEntity.Companion.TABLE_NAME

@Entity(primaryKeys = [LIST, SINGLE], tableName = TABLE_NAME)
class ListEntity (
        @ColumnInfo(name=LIST)
        val listUri : String,
        @ColumnInfo(name= SINGLE)
        val singleEntityUri: String
){
    companion object {
        const val LIST = "list_uri"
        const val SINGLE = "single_uri"
        const val TABLE_NAME = "list"
    }
}