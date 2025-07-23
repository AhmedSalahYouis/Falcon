package com.salah.falcon.domain

import com.salah.falcon.domain.usecase.GetLaunchDetailsUseCase
import com.salah.falcon.domain.repository.ILaunchesRepository
import com.salah.falcon.domain.model.LaunchDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LaunchDetailsUseCaseTest {
    private lateinit var repository: ILaunchesRepository
    private lateinit var useCase: GetLaunchDetailsUseCase

    @Before
    fun setUp() {
        repository = mock(ILaunchesRepository::class.java)
        useCase = GetLaunchDetailsUseCase(repository)
    }

    @Test
    fun `invoke returns expected flow`() = runBlocking {
        val details = LaunchDetails("1", mock(), mock(), "site")
        `when`(repository.getLaunchDetails("1")).thenReturn(details)
        val result = useCase.invoke("1").first()
        assertEquals(details, result)
    }

    @Test
    fun `repository getLaunchDetails is called with correct id`() { runBlocking {
        val details = LaunchDetails("2", mock(), mock(), "site2")
        `when`(repository.getLaunchDetails("2")).thenReturn(details)
        useCase.invoke("2").first()
        verify(repository).getLaunchDetails("2")
    }}

    @Test
    fun `invoke propagates error from repository`() {
        runBlocking {
            `when`(repository.getLaunchDetails("404")).thenThrow(RuntimeException("Not found"))
            assertThrows(RuntimeException::class.java) {
                runBlocking { useCase.invoke("404").first() }
            }
        }
    }
}
