package mobile.hospetall.ps.isel.hospetallmobile.adapter.viewholder

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

abstract class AbstractHolder<T : ViewDataBinding>(
        binder: T
) : RecyclerView.ViewHolder(binder.root)