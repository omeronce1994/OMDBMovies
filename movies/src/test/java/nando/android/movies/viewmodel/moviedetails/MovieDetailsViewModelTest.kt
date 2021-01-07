package nando.android.movies.viewmodel.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.spy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import nando.android.core.data.repository.moviedetails.MovieDetailsRepository
import nando.android.core.model.Resource
import nando.android.core.model.error.ExceptionErrorHandler
import nando.android.core.model.movies.MovieModel
import nando.android.movies.MainCoroutineRule
import nando.android.movies.MockedMovieDetailsRepository
import nando.android.movies.getOrAwaitValue
import nando.android.movies.model.moviedetails.mapper.MovieModelToMovieDetailsMapper
import nando.android.movies.model.moviedetails.mapper.MovieDetailsToMovieModelMapper
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito.`when`

class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var repository: MovieDetailsRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        repository = spy(MockedMovieDetailsRepository())
        viewModel = MovieDetailsViewModel(
            repository,
            MovieModelToMovieDetailsMapper(),
            MovieDetailsToMovieModelMapper(),
            TestCoroutineDispatcher()
        )
    }

    @Test
    fun `verify view has error state when get movie by id return error`() = runBlockingTest {
        val error = Resource.Error<MovieModel>(ExceptionErrorHandler(Exception()))
        `when`(repository.getMovieById("")).thenReturn(flow { emit(error) })
        viewModel.getMovie("")
        assertEquals(viewModel.liveData.getOrAwaitValue {  }.state, MovieDetailsViewModel.ViewState.Error)
    }

    @Test
    fun `verify view has ShowDetails state when get movie by id return sucess`() = runBlockingTest {
        val movie = MovieModel()
        val success = Resource.Success(movie)
        `when`(repository.getMovieById("")).thenReturn(flow { emit(success) })
        viewModel.getMovie("")
        assertEquals(viewModel.liveData.getOrAwaitValue {  }.state, MovieDetailsViewModel.ViewState.ShowDetails)
    }

    @Test
    fun `verify UiState data is correct when getting movie by id`() = mainCoroutineRule.runBlockingTest {
        val movie = MovieModel("11", "test")
        val expected = MovieModelToMovieDetailsMapper().map(movie)
        repository.saveMovie(movie).collect {  }
        viewModel.getMovie("11")
        assertEquals(viewModel.liveData.getOrAwaitValue {  }.data, expected)
    }

}