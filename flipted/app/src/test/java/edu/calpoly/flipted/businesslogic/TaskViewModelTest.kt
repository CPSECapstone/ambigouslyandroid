package edu.calpoly.flipted.businesslogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import edu.calpoly.flipted.ui.tasks.TaskViewModel
import org.junit.runner.RunWith
import org.junit.*

@RunWith(AndroidJUnit4::class)
class TaskViewModelTest {
    @Mock
    private lateinit var viewModel: TaskViewModel

    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>

    @Mock
    private lateinit var observer: Observer<in Boolean>

    // 3
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // 4
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(QuotesViewModel(ApplicationProvider.getApplicationContext(),
            QuotesRepositoryImpl(ApplicationProvider.getApplicationContext())))
        isLoadingLiveData = viewModel.dataLoading
    }
}