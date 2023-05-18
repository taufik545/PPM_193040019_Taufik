package id.ac.unpas.functionalcompose.persistences
import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.unpas.functionalcompose.model.SetoranSampah

@Dao
interface SetoranSampahDao {
    @Query("SELECT * FROM SetoranSampah ORDER BY tanggal DESC")
    fun loadAll(): LiveData<List<SetoranSampah>>

    @Query("SELECT * FROM SetoranSampah ORDER BY tanggal DESC")
    suspend fun getList(): List<SetoranSampah>

    @Query("SELECT * FROM SetoranSampah WHERE id = :id")
    suspend fun find(id: String): SetoranSampah?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: SetoranSampah)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<SetoranSampah>)

    @Delete
    fun delete(item: SetoranSampah)

    @Query("DELETE FROM SetoranSampah WHERE id = :id")
    fun delete(id: String)
}