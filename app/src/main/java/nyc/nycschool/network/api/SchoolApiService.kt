package nyc.nycschool.network.api

import nyc.nycschool.network.model.SatModel
import nyc.nycschool.network.model.SchoolModel
import retrofit2.Response
import retrofit2.http.GET

interface SchoolApiService {
    @GET("/resource/s3k6-pzi2.json/")
    suspend fun getSchools(): Response<List<SchoolModel>>

    @GET("/resource/f9bf-2cp4.json/")
    suspend fun getSats(): Response<List<SatModel>>
}
