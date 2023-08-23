package nyc.nycschool

import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import nyc.nycschool.network.model.SatModel
import nyc.nycschool.repository.NycSchoolRepository
import nyc.nycschool.viewmodel.NycSchoolViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class NycSchoolViewModelTest {

    @Test
    fun getSATScore_apiCallSuccess_setData()  {
        //Given
        val mainRepository = mockk<NycSchoolRepository>()
        val mockedSatModel = SatModel(
            id = "school_123",
            schoolName = "Test School",
            readingScore = "444",
            mathScore = "333",
            writingScore = "400"
        )
        val schoolId = "school_123"
        every { mainRepository.getNycSchoolSats(schoolId) } returns Observable.just(mockedSatModel)

        val subject = NycSchoolViewModel(mainRepository, Schedulers.trampoline(), Schedulers.trampoline())

        // When
        subject.getSATScore(schoolId)

        // Then
        assertEquals(false, subject.loading)
        assertEquals(false, subject.apiCallError)
        assertEquals(mockedSatModel, subject.satModel)
    }
}

