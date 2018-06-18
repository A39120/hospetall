package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment

/**
 * Interface implemented when you want to use a callback for
 * a [Treatment] list. The class that will implement this
 * callback should be cast to this callback when the method
 * [onTreatmentList] is called.
 */
interface OnTreatmentListListener {
    fun onTreatmentList(onList: (List<Treatment>) -> Unit)
}