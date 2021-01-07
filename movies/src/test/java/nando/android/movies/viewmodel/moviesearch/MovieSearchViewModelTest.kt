package nando.android.movies.viewmodel.moviesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import nando.android.core.data.repository.moviesearch.MovieSearchRepository
import nando.android.movies.MainCoroutineRule
import nando.android.movies.di.moviesearch.movieSearchModule
import nando.android.movies.getOrAwaitValue
import nando.android.movies.paging.MovieThumbnailPagingDataSource
import nando.android.movies.paging.MovieThumbnailPagingDataSourceFactory
import nando.android.movies.util.MoviesConstants
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin

class MovieSearchViewModelTest {

    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var repository: MovieSearchRepository
    private lateinit var pagingFactory: MovieThumbnailPagingDataSourceFactory
    private lateinit var pagingDataSource: MovieThumbnailPagingDataSource

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin {
            modules(movieSearchModule)
        }
        repository = mock()
        val coroutineScope = TestCoroutineScope()
        pagingDataSource = MovieThumbnailPagingDataSource(
            repository,
            coroutineScope,
            KoinContextHandler.get().get(),
            ""
        )
        pagingFactory = MovieThumbnailPagingDataSourceFactory(
            pagingDataSource,
            coroutineScope
        )
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(MoviesConstants.PAGE_SIZE)
            .setPageSize(MoviesConstants.PAGE_SIZE).build()
        viewModel = MovieSearchViewModel(config, pagingFactory, TestCoroutineDispatcher())
    }

    @Test
    fun `verify view state is loading when typing query`() = runBlockingTest {
        viewModel.search("test")
        assertEquals(viewModel.uiState.getOrAwaitValue {  }.state, MovieSearchViewModel.ViewState.Loading)
    }
}