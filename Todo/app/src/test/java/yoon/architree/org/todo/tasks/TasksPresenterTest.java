package yoon.architree.org.todo.tasks;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import yoon.architree.org.todo.data.Task;
import yoon.architree.org.todo.data.source.TasksDataSource;
import yoon.architree.org.todo.data.source.TasksRepository;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the implementation of {@link TasksPresenter}
 */
public class TasksPresenterTest {

    private static List<Task> TASKS;
    
    @Mock
    private TasksRepository mTasksRepository;
    
    @Mock
    private TasksContract.View mTasksView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<TasksDataSource.LoadTasksCallback> mLoadTasksCallbackCaptor;
    
    private TasksPresenter dut;
    
    @Before
    public void setUp() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        
        // Get a reference to the class under test
        dut = new TasksPresenter(mTasksRepository, mTasksView);
        
        // The presenter won't update the view unless it's active
        when(mTasksView.isActive()).thenReturn(true);
        
        // We start the tasks to 3, with one active and two completed
        TASKS = Lists.newArrayList(
                    new Task("Title1", "Description1"),
                    new Task("Title2", "Description2", true),
                    new Task("Title3", "Description3", true));
    }
    
    @Test
    public void loadAllTasksFromRepositoryAndLoadIntoView() {
        // Given an initialized TasksPresenter with initialized tasks
        // When loading of Tasks is requested
        dut.setFiltering(TasksFilterType.ALL_TASKS);
        dut.loadTasks(true);
        
        // Callback is captured and invoked with stubbed tasks
        verify(mTasksRepository).getTasks(mLoadTasksCallbackCaptor.capture());
        mLoadTasksCallbackCaptor.getValue().onTasksLoaded(TASKS);
        
        // Then progress indicator is shown
        verify(mTasksView).setLoadingIndicator(true);
        // Then progress indicator is hidden and all tasks are shown in UI
        verify(mTasksView).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mTasksView).showTasks(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 3);
    }
    
    @Test
    public void loadActiveTasksFromRepositoryAndLoadIntoView() {
        // Given an initialized TasksPresenter with initialized tasks
        // When loading of Tasks is requested
        dut.setFiltering(TasksFilterType.ACTIVE_TASKS);
        dut.loadTasks(true);
        
        // Callback is captured and invoked with stubbed tasks
        verify(mTasksRepository).getTasks(mLoadTasksCallbackCaptor.capture());
        mLoadTasksCallbackCaptor.getValue().onTasksLoaded(TASKS);
        
        // Then progress indicator is hidden and active tasks are shown in UI
        verify(mTasksView).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mTasksView).showTasks(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 1);
    }

    @Test
    public void loadCompletedTasksFromRepositoryAndLoadIntoView() {
        // Given an initialized TasksPresenter with initialized tasks
        // When loading of Tasks is requested
        dut.setFiltering(TasksFilterType.COMPLETED_TASKS);
        dut.loadTasks(true);

        // Callback is captured and invoked with stubbed tasks
        verify(mTasksRepository).getTasks(mLoadTasksCallbackCaptor.capture());
        mLoadTasksCallbackCaptor.getValue().onTasksLoaded(TASKS);

        // Then progress indicator is hidden and completed tasks are shown in UI
        verify(mTasksView).setLoadingIndicator(false);
        ArgumentCaptor<List> showTasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mTasksView).showTasks(showTasksArgumentCaptor.capture());
        assertTrue(showTasksArgumentCaptor.getValue().size() == 2);
    }

    @Test
    public void clickOnFab_ShowsAddTaskUi() {
        // When adding a new task
        dut.addNewTask();

        // Then add task UI is shown
        verify(mTasksView).showAddTask();
    }

    @Test
    public void clickOnTask_ShowsDetailUi() {
        // Given a stubbed active task
        Task requestedTask = new Task("Details Requested", "For this task");

        // When open task details is requested
        dut.openTaskDetails(requestedTask);

        // Then task detail UI is shown
        verify(mTasksView).showTaskDetailsUi(any(String.class));
    }

    @Test
    public void completeTask_ShowsTaskMarkedComplete() {
        // Given a stubbed task
        Task task = new Task("Details Requested", "For this task");

        // When task is marked as complete
        dut.completeTask(task);

        // Then repository is called and task marked complete UI is shown
        verify(mTasksRepository).completeTask(task);
        verify(mTasksView).showTaskMarkedComplete();
    }

    @Test
    public void activateTask_ShowsTaskMarkedActive() {
        // Given a stubbed completed task
        Task task = new Task("Details Requested", "For this task", true);
        dut.loadTasks(true);

        // When task is marked as activated
        dut.activateTask(task);

        // Then repository is called and task marked active UI is shown
        verify(mTasksRepository).activateTask(task);
        verify(mTasksView).showTaskMarkedActive();
    }

    @Test
    public void unavailableTasks_ShowsError() {
        // When tasks are loaded
        dut.setFiltering(TasksFilterType.ALL_TASKS);
        dut.loadTasks(true);

        // And the tasks aren't available in the repository
        verify(mTasksRepository).getTasks(mLoadTasksCallbackCaptor.capture());
        mLoadTasksCallbackCaptor.getValue().onDataNotAvailable();

        // Then an error message is shown
        verify(mTasksView).showLoadingTasksError();
    }
}