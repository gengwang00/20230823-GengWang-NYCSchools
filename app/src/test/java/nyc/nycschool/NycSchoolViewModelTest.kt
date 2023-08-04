package nyc.nycschool

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nyc.nycschool.network.model.SatModel
import nyc.nycschool.repository.MainRepository
import nyc.nycschool.viewmodel.NycSchoolViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NycSchoolViewModelTest {
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun getSATScore_apiCallSuccess_setData() = runTest {
        //Given
        val mainRepository = mockk<MainRepository>()
        val mockedSatModel = SatModel(
            id = "school_123",
            schoolName = "Test School",
            readingScore = "444",
            mathScore = "333",
            writingScore = "400"
        )
        val schoolId = "school_123"
        coEvery { mainRepository.getaSatScore(schoolId) } returns mockedSatModel

        val subject = NycSchoolViewModel(mainRepository)

        // When
        subject.getSATScore(schoolId)

        // Then
        assertEquals(false, subject.loading)
        assertEquals(false, subject.apiCallSatError)
        assertEquals(mockedSatModel, subject.satModel)
    }
}

