import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.whatdidsay.DataBase.AppDatabase
import com.example.whatdidsay.DataBase.HadithDao
import com.example.whatdidsay.Models.Hadith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray

class HadithRepository(private val context: Context) {

    private val hadithDao: HadithDao
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        val db = AppDatabase.getDatabase(context, scope)
        hadithDao = db.hadithDao()
        scope.launch { initializeDatabase() }
    }

    fun getAllHadiths(): Flow<PagingData<Hadith>> {
        return Pager(PagingConfig(pageSize = 20)) {
            hadithDao.getAllHadiths()
        }.flow
    }

    fun searchDatabase(searchQuery: String): Flow<PagingData<Hadith>> {
        return Pager(PagingConfig(pageSize = 20)) {
            hadithDao.searchDatabase(searchQuery)
        }.flow
    }

    suspend fun insert(hadith: Hadith) = hadithDao.insert(hadith)

    suspend fun deleteAllHadiths() {
        hadithDao.deleteAllHadiths()
    }

    suspend fun initializeDatabase() {
        Log.d("HadithRepository", "Initializing database")  // Log when this function is called

        if (hadithDao.count() == 0) {
            Log.d("HadithRepository", "Hadith count is 0, populating database from JSON")  // Log the Hadith count
            populateDatabaseFromJson(context)
        }
    }

    private suspend fun populateDatabaseFromJson(context: Context) {
        val jsonString = context.assets.open("hadiths.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        Log.d("HadithRepository", "Total Hadiths in JSON file: ${jsonArray.length()}")  // Log the total number of Hadiths in the JSON file

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val hadithText = jsonObject.getString("Hadith")
            val keywordsJsonArray = jsonObject.getJSONArray("Keywords")
            val keywords = StringBuilder()
            for (j in 0 until keywordsJsonArray.length()) {
                keywords.append(keywordsJsonArray.getString(j))
                if (j < keywordsJsonArray.length() - 1) {
                    keywords.append(",")
                }
            }
            val hadith = Hadith(text = hadithText, keywords = keywords.toString())
            hadithDao.insert(hadith)
            Log.d("HadithRepository", "Inserted Hadith: $hadith")  // Log each Hadith as it's inserted into the database
        }
    }
}