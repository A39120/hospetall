package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.util.LruCache
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.TreatmentDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Treatment
import org.json.JSONObject

class TreatmentAccess
    : AbstractListAccess<Treatment, TreatmentDao>("treatmentList") {
    companion object {
        private val mInstance by lazy { TreatmentAccess() }
        fun getInstance() = mInstance
    }

    private val cache by lazy { LruCache<String, Value<Treatment>>(mCacheSize) }
    private val listCache by lazy { LruCache<String, Value<List<Treatment>>>(mCacheSize/10)}

    override fun getCollectionCache() = listCache
    override fun getSingleCache() = cache
    override fun getDao(database: MobileDatabase) = database.treatmentDao()

    override fun parse(json: JSONObject) = Treatment.parse(json)

}
