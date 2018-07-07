package mobile.hospetall.ps.isel.hospetallmobile.dataaccess

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao.PetDao
import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet
import org.json.JSONObject

/**
 * Class for accessing information on a specified request
 * related to Pet.
 */
class PetAccess
    : AbstractListAccess<Pet, PetDao>("petList") {

    override fun getDao(database: MobileDatabase) = database.petDao()

    /**
     * Parses Json object to [Pet]
     * @param json: JSON object
     * @return [Pet] object
     */
    override fun parse(json: JSONObject) = Pet.parse(json)
}
