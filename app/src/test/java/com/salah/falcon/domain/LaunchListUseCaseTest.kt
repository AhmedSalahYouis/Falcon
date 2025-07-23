package com.salah.falcon.domain

import androidx.paging.PagingData
import com.salah.falcon.domain.usecase.GetLaunchListUseCase
import com.salah.falcon.domain.repository.ILaunchesRepository
import com.salah.falcon.domain.model.LaunchSummary
import com.salah.falcon.domain.model.Mission
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LaunchListUseCaseTest {
    private lateinit var repository: ILaunchesRepository
    private lateinit var useCase: GetLaunchListUseCase

    @Before
    fun setUp() {
        repository = mock(ILaunchesRepository::class.java)
        useCase = GetLaunchListUseCase(repository)
    }

    @Test
    fun `invoke returns expected flow`() = runBlocking {
        val expectedFlow: Flow<PagingData<LaunchSummary>> = flowOf(
            PagingData.from(
                listOf(
                    LaunchSummary(
                        "1",
                        "Falcon 9",
                        Mission("test mission", "")
                    )
                )
            )
        )
        `when`(repository.getLaunchesList(10)).thenReturn(expectedFlow)
        val result = useCase.invoke(GetLaunchListUseCase.Params(10))
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `invoke with different params returns correct flow`() = runBlocking {
        val expectedFlow: Flow<PagingData<LaunchSummary>> = flowOf(
            PagingData.from(
                listOf(
                    LaunchSummary(
                        "2",
                        "Falcon 1",
                        Mission("test mission", "")
                    )
                )
            )
        )
        `when`(repository.getLaunchesList(5)).thenReturn(expectedFlow)
        val result = useCase.invoke(GetLaunchListUseCase.Params(5))
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `invoke returns empty flow when no launches`() = runBlocking {
        val expectedFlow: Flow<PagingData<LaunchSummary>> = flowOf(PagingData.empty())
        `when`(repository.getLaunchesList(0)).thenReturn(expectedFlow)
        val result = useCase.invoke(GetLaunchListUseCase.Params(0))
        assertEquals(expectedFlow, result)
    }

    @Test
    fun `invoke propagates error from repository`() = runBlocking {
        `when`(repository.getLaunchesList(-1)).thenThrow(IllegalArgumentException("Invalid page size"))
        try {
            useCase.invoke(GetLaunchListUseCase.Params(-1))
            assert(false)
        } catch (e: IllegalArgumentException) {
            assertEquals("Invalid page size", e.message)
        }
    }
}