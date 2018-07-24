package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Client

interface OnClientListener {
    fun onClient(listener: (Client) -> Unit)
}