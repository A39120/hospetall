package mobile.hospetall.ps.isel.hospetallmobile.activities.viewmodel

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import mobile.hospetall.ps.isel.hospetallmobile.HospetallApplication
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.ClientAccess
import mobile.hospetall.ps.isel.hospetallmobile.models.Client
import mobile.hospetall.ps.isel.hospetallmobile.utils.values.UriUtils

class ProfileViewModel(application: HospetallApplication) : AndroidViewModel(application) {

    private val clientRepo by lazy { ClientAccess(application) }

    private var client : LiveData<Client>? = null

    fun init(id: Int){
        val uri = UriUtils.getClientUri(id).build().toString()
        client = clientRepo.get(uri)
    }

    fun getClient() = client
}