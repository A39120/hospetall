package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

/**
 * Interface implemented when you want to use a callback for
 * a [Pet] object. The class that will implement this
 * callback should be cast to this callback before the method
 * [onPet] is called.
 */
interface OnPetListener {
    fun onPet(onPet: (Pet) -> Unit)
}