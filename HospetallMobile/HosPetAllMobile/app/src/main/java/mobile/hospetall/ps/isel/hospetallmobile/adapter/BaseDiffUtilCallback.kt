package mobile.hospetall.ps.isel.hospetallmobile.adapter

import android.support.v7.util.DiffUtil
import mobile.hospetall.ps.isel.hospetallmobile.models.base.Base

class BaseDiffUtilCallback<T: Base>(
        private val newList: List<T>,
        private val oldList : List<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].uri == newList[newItemPosition].uri &&
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]


}
