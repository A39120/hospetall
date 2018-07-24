package mobile.hospetall.ps.isel.hospetallmobile.utils.listeners

import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation

/**
 * Interface implemented when you want to use a callback for
 * a [Consultation] list. The class that will implement this
 * callback should be cast to this callback when
 * [onConsultationList] is called.
 */
interface OnConsultationListListener {
    fun onConsultationList(list: (List<Consultation>) -> Unit)
}