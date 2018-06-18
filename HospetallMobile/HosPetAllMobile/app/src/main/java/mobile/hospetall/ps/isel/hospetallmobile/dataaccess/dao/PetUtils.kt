package mobile.hospetall.ps.isel.hospetallmobile.dataaccess.dao

import mobile.hospetall.ps.isel.hospetallmobile.dataaccess.database.MobileDatabase
import mobile.hospetall.ps.isel.hospetallmobile.models.Pet

/**
 * Utils for pets.
 */
object PetUtils {

    /**
     * Updates pet table by comparing current table with
     * the previous one.
     *
     * @param dao: Pet data access object
     * @param newPetList: The new pet list for pets
     */
    fun updatePetTable(database: MobileDatabase, newPetList: List<Pet>) {
        database.beginTransaction()

        val dao: PetDao = database.petDao
        val list = dao.getAll()

        val delete = list.filter {
            val pet = it
            return@filter !newPetList.contains(pet) || newPetList.find { pet.id == it.id } != null
        }
        delete.forEach{ dao.deleteById(it.uri) }

        val toInsertOrUpdate = newPetList.filter(list::contains)
        toInsertOrUpdate.forEach(dao::insertOrUpdate)

        database.endTransaction()
    }

}