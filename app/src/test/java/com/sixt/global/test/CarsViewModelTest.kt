package com.sixt.global.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sixt.global.cars.repository.CarsRepository
import com.sixt.global.cars.viewmodel.CarsViewModel
import com.sixt.global.cars.viewmodel.CarsViewModelImpl
import com.sixt.global.util.FakeListsDataSource
import com.sixt.global.util.TestDispatchers
import com.sixt.global.common.dispatcher.CoroutineDispatchers
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CarsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CarsRepository

    private lateinit var dispatchers: CoroutineDispatchers
    private lateinit var listsDataSource: FakeListsDataSource
    private lateinit var viewModel: CarsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        dispatchers = TestDispatchers()
        listsDataSource = FakeListsDataSource

        viewModel = CarsViewModelImpl(repository, dispatchers)
    }

    @Test
    fun `when action is RequestCars should request all cars successfully`() {
        coEvery { repository.requestCars() } returns listsDataSource.getFakeCars()

        viewModel.perform(CarsViewModel.Action.RequestCars)

        coVerify { repository.requestCars() }

        viewModel.getStateStream().observeForever {
            assertEquals(listsDataSource.getFakeCars()[0], it.carsList[0])
            assertEquals(listsDataSource.getFakeCars()[1], it.carsList[1])
            assertFalse(it.showError)
            assertTrue(it.moveCamera)
        }
    }

    @Test
    fun `when action is RequestCars with error should display the error`() {
        coEvery { repository.requestCars() } throws Exception("Connection error")

        viewModel.perform(CarsViewModel.Action.RequestCars)

        coVerify { repository.requestCars() }

        viewModel.getStateStream().observeForever {
            assertEquals("Connection error", it.errorMessage)
            assertTrue(it.showError)
            assertFalse(it.moveCamera)
        }
    }
}