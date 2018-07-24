package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Event

interface OnEventListListener {
    fun onEventListListener(listener: (List<Event>?) -> Unit, type: Int)
}