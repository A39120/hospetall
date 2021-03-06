package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import android.util.LruCache
import android.content.Context
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.PetDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import org.json.JSONObject

/**
 * Class for accessing information on a specified request
 * related to Pet.
 */
class PetAccess(context: Context)
    : AbstractListAccess<Pet, PetDao>(context,"petList") {

    private val cache by lazy { LruCache<String, Value<Pet>>(mCacheSize) }
    private val listCache by lazy { LruCache<String, Value<List<Pet>>>(mCacheSize/10)}

    override fun getCollectionCache() = listCache
    override fun getSingleCache() = cache

    override fun getDao(database: MobileDatabase) = database.petDao()

    /**
     * Parses Json object to [Pet]
     * @param json: JSON object
     * @return [Pet] object
     */
    override fun parse(json: JSONObject) = Pet.parse(json)

}
