package com.salah.falcon.data

import androidx.paging.PagingData
import com.salah.falcon.data.repository.LaunchesRepository
import com.salah.falcon.data.datasource.ILaunchesRemoteDataSource
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.model.Mission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LaunchesRepositoryTest {
    private lateinit var remoteDataSource: ILaunchesRemoteDataSource
    private lateinit var repository: LaunchesRepository

    @Before
    fun setUp() {
        remoteDataSource = mock(ILaunchesRemoteDataSource::class.java)
        repository = LaunchesRepository(remoteDataSource)
    }

    @Test
    fun `getLaunchesList returns expected flow`() = runBlocking {
        val expectedFlow: Flow<PagingData<LaunchSummary>> = flowOf(
            PagingData.from(
                listOf(
                    LaunchSummary(
                        "1", "Falcon 9",
                        Mission("Starlink-15", "")
                    )
                )
            )
        )
        `when`(remoteDataSource.getLaunchList(10)).thenReturn(expectedFlow)
        val result = repository.getLaunchesList(10)
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `getLaunchDetails returns expected details`() = runBlocking {
        val details = LaunchDetails("1", mock(), mock(), "site")
        `when`(remoteDataSource.getLaunchDetails("1")).thenReturn(details)
        val result = repository.getLaunchDetails("1")
        assertEquals(details, result)
    }

    @Test
    fun `getLaunchDetails throws when not found`(): Unit = runBlocking {
        `when`(remoteDataSource.getLaunchDetails("404")).thenThrow(RuntimeException("Not found"))
        assertThrows(RuntimeException::class.java) {
            runBlocking { repository.getLaunchDetails("404") }
        }
    }
}
