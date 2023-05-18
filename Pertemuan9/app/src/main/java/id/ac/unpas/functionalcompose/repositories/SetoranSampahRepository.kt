package id.ac.unpas.functionalcompose.repositories
import com.benasher44.uuid.uuid4
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import id.ac.unpas.functionalcompose.model.SetoranSampah
import id.ac.unpas.functionalcompose.networks.SetoranSampahApi
import id.ac.unpas.functionalcompose.persistences.SetoranSampahDao
import javax.inject.Inject
interface Repository

class SetoranSampahRepository @Inject constructor(
    private val api: SetoranSampahApi,
    private val dao: SetoranSampahDao
) : Repository {

    suspend fun loadItems(onSuccess: (List<SetoranSampah>) -> Unit,
                          onError: (List<SetoranSampah>, String) -> Unit
    ) {
        val list: List<SetoranSampah> = dao.getList()
        api.all()
            // handle the case when the API request gets a  success response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    it.data?.let { list ->
                        dao.insertAll(list)
                        val items: List<SetoranSampah> =
                            dao.getList()
                        onSuccess(items)
                    }
                }
            }
// handle the case when the API request gets an error response.
            // e.g. internal server error.
            .suspendOnError {
                onError(list, message())
            }
            // handle the case when the API request gets an exception response.
            // e.g. network connection error.
            .suspendOnException {
                onError(list, message())
            }
    }
    suspend fun insert(
        tanggal: String,
        nama: String,
        berat: String,
        onSuccess: (SetoranSampah) -> Unit,
        onError: (SetoranSampah?, String) -> Unit
    ) {
        val id = uuid4().toString()
        val item = SetoranSampah(id, tanggal, nama, berat)
        dao.insertAll(item)
        api.insert(item)
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
            // handle the case when the API request gets an error response. // e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
            // handle the case when the API request gets an exception response. // e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }
    suspend fun update(
        id: String,
        tanggal: String,
        nama: String,
        berat: String,
        onSuccess: (SetoranSampah) -> Unit,
        onError: (SetoranSampah?, String) -> Unit
    ) {
        val item = SetoranSampah(id, tanggal, nama, berat)
        dao.insertAll(item)
        api.update(id, item)
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
            // handle the case when the API request gets an error response.
            // e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
            // handle the case when the API request gets an exception response.
            // e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }
    suspend fun delete(id: String, onSuccess: () -> Unit,
                       onError: (String) -> Unit) {
        dao.delete(id)
        api.delete(id)
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    onSuccess()
                }
            }
            // handle the case when the API request gets an error response.
            // e.g. internal server error.
            .suspendOnError {
                onError(message())
            }
// handle the case when the API request gets an exception response.
            // e.g. network connection error.
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun find(id: String) : SetoranSampah? {
        return dao.find(id)
    }
}

