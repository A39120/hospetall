package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.util.LruCache
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.ConsultationDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Consultation
import org.json.JSONObject

class ConsultationAccess
    : AbstractListAccess<Consultation, ConsultationDao>("consultationList") {
    companion object {
        private val mInstance by lazy { ConsultationAccess() }
        fun getInstance() = mInstance
    }

    private val cache by lazy { LruCache<String, Value<Consultation>>(mCacheSize)}
    private val listCache by lazy { LruCache<String, Value<List<Consultation>>>(mCacheSize/10)}

    override fun getCollectionCache() = listCache
    override fun getSingleCache() = cache

    override fun getDao(database: MobileDatabase) = database.consultationDao()

    override fun parse(json: JSONObject): Consultation = Consultation.parse(json)

}