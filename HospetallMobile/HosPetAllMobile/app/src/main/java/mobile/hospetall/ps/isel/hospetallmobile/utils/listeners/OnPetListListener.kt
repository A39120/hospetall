package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

interface OnPetListListener {
    fun onPetList(listener: (List<Pet>) -> Unit)
}