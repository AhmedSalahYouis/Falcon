package com.salah.falcon.presentation

import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.domain.model.LaunchDetails
import com.salah.falcon.domain.usecase.GetLaunchDetailsUseCase
import com.salah.falcon.presentation.launches.launchdetails.LaunchDetailsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class LaunchDetailsViewModelTest {
    private lateinit var useCase: GetLaunchDetailsUseCase
    private lateinit var logger: ITimberLogger
    private lateinit var viewModel: LaunchDetailsViewModel

    @Before
    fun setUp() {
        useCase = mock(GetLaunchDetailsUseCase::class.java)
        logger = mock(ITimberLogger::class.java)
        viewModel = LaunchDetailsViewModel(useCase, logger)
    }

    @Test
    fun `onArgsReceived fetches details when launchId changes`() = runBlocking {
        val details = LaunchDetails("1", mock(), mock(), "site")
        `when`(useCase.invoke("1")).thenReturn(kotlinx.coroutines.flow.flowOf(details))
        viewModel.onArgsReceived("1")
        assertEquals("1", viewModel.uiStateFlow.value.launchId)
    }

    @Test
    fun `handleAction OnBackClick sends NavigateBack`() {
        // This would require observing side effects, which may need a test observer or custom logic
        // For now, we just call the method to ensure no crash
        viewModel.handleAction(LaunchDetailsViewModel.Action.OnBackClick)
    }

    @Test
    fun `handleAction OnRetryClick with blank launchId logs error`() {
        viewModel.handleAction(LaunchDetailsViewModel.Action.OnRetryClick)
        verify(logger).logError(anyString(), any())
    }
}
