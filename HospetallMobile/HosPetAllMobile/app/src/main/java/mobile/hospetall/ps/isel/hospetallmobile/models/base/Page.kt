package mobile.hospetall.ps.isel.hospetallmobile.models.base

import org.json.JSONObject
import java.util.*

data class  Page<T>(
        val objList: List<T> ,
        val maxSizeOfPage: Int,
        val totalElements : Int,
        val totalPages : Int,
        val currentPage : Int
){
    companion object {

        private const val EMBEDDED = "_embedded"
        private const val PAGE  = "page"
        private const val MAX_PAGE_SIZE = "size"
        private const val TOTAL_ELEMENTS = "totalElements"
        private const val TOTAL_PAGES = "totalPages"
        private const val CURRENT_PAGE = "number"

        fun <T> parse(obj: JSONObject, embeddedParser: (JSONObject) -> T) : Page<T>{
            val elements = obj.getJSONArray(EMBEDDED)
            val list = LinkedList<T>()
            for(i : Int in 0 .. elements.length())
                list.add(embeddedParser(elements.getJSONObject(i)))

            val page = obj.getJSONObject(PAGE)
            val maxSizeOfPage = page.getInt(MAX_PAGE_SIZE)
            val totalElements = page.getInt(TOTAL_ELEMENTS)
            val totalPages = page.getInt(TOTAL_PAGES)
            val currentPage = page.getInt(CURRENT_PAGE)

            return Page(list, maxSizeOfPage, totalElements, totalPages, currentPage)
        }
    }

}