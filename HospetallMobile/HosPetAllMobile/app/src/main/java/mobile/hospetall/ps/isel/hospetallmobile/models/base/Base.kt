package mobile.hospetall.ps.isel.hospetallmobile.models.base

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.DatabaseColumns

open class Base(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name=DatabaseColumns.URI)
        val uri: String,
        @ColumnInfo(name = DatabaseColumns.ID)
        val id: Int
)