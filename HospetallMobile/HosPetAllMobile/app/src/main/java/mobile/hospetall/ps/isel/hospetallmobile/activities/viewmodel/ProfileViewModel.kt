package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ClientAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ProfileViewModel : ViewModel() {

    private val clientRepo by lazy { ClientAccess.getInstance() }
    private var client : LiveData<Client>? = null

    fun init(id: Int){
        val uri = UriUtils.getClientUri(id).build().toString()
        client = clientRepo.get(uri)
    }

    fun getClient() = client
}