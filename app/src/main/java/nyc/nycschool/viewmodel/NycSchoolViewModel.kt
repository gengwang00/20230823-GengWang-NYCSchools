package nyc.nycschool.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nyc.nycschool.network.model.NetworkResult
import nyc.nycschool.network.model.SatModel
import nyc.nycschool.network.model.SchoolModel
import nyc.nycschool.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class NycSchoolViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel(), DefaultLifecycleObserver {

    val snapshotStateList = SnapshotStateList<SchoolModel>()
    var loading: Boolean by mutableStateOf(false)
    var apiCallError: Boolean by mutableStateOf(false)
    var apiCallSatError: Boolean by mutableStateOf(false)
    var satModel: SatModel? by mutableStateOf(null)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        getNycSchools()
    }

    fun getSATScore(schooldID: String) {
        viewModelScope.launch {
            loading = true
            apiCallSatError = false
            satModel = null

            val result = mainRepository.getaSatScore(schooldID )

            result?.let{
                satModel = it
            }
            if (satModel == null) {
                        apiCallSatError = true
                    }

            loading = false
        }
    }

    private fun getNycSchools() = viewModelScope.launch {
        loading = true

        when (val result = mainRepository.getAllSchools()) {
            is NetworkResult.Success -> {
                result.data?.let { list ->
                    val sortedList = list.sortedWith(compareBy { it.title })
                    snapshotStateList.addAll(sortedList)
                }

                // Edge case: API call is successful but no data returned
                if (snapshotStateList.isEmpty()) {
                    apiCallError = true
                }
            }

            else -> {
                apiCallError = true
            }
        }
        loading = false
    }
}