package nyc.nycschool.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import nyc.nycschool.di.IoScheduler
import nyc.nycschool.di.MainScheduler
import nyc.nycschool.network.model.SatModel
import nyc.nycschool.network.model.SchoolModel
import nyc.nycschool.repository.NycSchoolRepository
import javax.inject.Inject


@HiltViewModel
class NycSchoolViewModel @Inject constructor(
    private val nycSchoolRepository: NycSchoolRepository,
    @IoScheduler private val ioScheduler: Scheduler,
    @MainScheduler private val mainScheduler: Scheduler
) :     ViewModel(), DefaultLifecycleObserver {

    val snapshotStateList = SnapshotStateList<SchoolModel>()

    var loading: Boolean by mutableStateOf(false)
    var apiCallError: Boolean by mutableStateOf(false)
    var satModel: SatModel? by mutableStateOf(null)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        getNycSchools()
    }

    fun getSATScore(schooldID: String) {
        loading = true
        apiCallError = false
        satModel = null

        nycSchoolRepository.getNycSchoolSats(schooldID)
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { satModel = it; }, { apiCallError = true }, { loading = false })
    }

    private fun getNycSchools() {
        loading = true
        apiCallError = false

        nycSchoolRepository.getNycSchools()
            .map { a -> a.sortedBy { it.title } }
            .subscribeOn(ioScheduler)
            .observeOn(mainScheduler)
            .subscribe(
                { list -> snapshotStateList.addAll(list) },
                { apiCallError = true },
                { loading = false })
    }
}