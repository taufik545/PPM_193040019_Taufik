package id.ac.unpas.functionalcompose.networks
import com.skydoves.sandwich.ApiResponse
import id.ac.unpas.functionalcompose.model.SetoranSampah
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface SetoranSampahApi {
    @GET("api/setoran-sampah")
    suspend fun all(): ApiResponse<SetoranSampahGetResponse>
    @GET("api/setoran-sampah/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<SetoranSampahSingleGetResponse>
    @POST("api/setoran-sampah")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: SetoranSampah):
            ApiResponse<SetoranSampahSingleGetResponse>
    @PUT("api/setoran-sampah/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: SetoranSampah):
            ApiResponse<SetoranSampahSingleGetResponse>
    @DELETE("api/setoran-sampah/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<SetoranSampahSingleGetResponse>
}