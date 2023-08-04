package nyc.nycschool.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nyc.nycschool.network.model.NetworkResult
import nyc.nycschool.network.api.SchoolApiService
import nyc.nycschool.network.model.SatModel
import nyc.nycschool.network.model.SchoolModel
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class MainRepository @Inject constructor(
    private val schoolApiService: SchoolApiService,
    private val defaultDispatcher: CoroutineDispatcher
) {

    // Should consider to use Room database to cache
    private var cachedSatScoreList: MutableList<SatModel> = mutableListOf()

    suspend private fun <T> apiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val resp = apiCall()
            if (resp.isSuccessful) {
                val body = resp.body()
                body?.let {
                    return NetworkResult.Success(it)
                }
            }
            return error("${resp.code()} ${resp.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")

    suspend fun getAllSchools(): NetworkResult<List<SchoolModel>> {
        return withContext(defaultDispatcher) { apiCall { schoolApiService.getSchools() } }
    }

    suspend fun getAllSats(): NetworkResult<List<SatModel>> {
        return withContext(defaultDispatcher) { apiCall { schoolApiService.getSats() } }
    }

    suspend fun getaSatScore(idIn: String): SatModel? {
        if (cachedSatScoreList.isEmpty()) {
            setCahedScoreList()
        }

        return cachedSatScoreList.find { it.id == idIn }
    }

    private suspend fun setCahedScoreList() {
        val result = getAllSats()

        when (result) {
            is NetworkResult.Success -> {
                result.data?.let {
                    cachedSatScoreList.addAll(it)
                }
            }
            is NetworkResult.Error -> {
                // Log error: result.errorMessage
            }
        }
    }
}
