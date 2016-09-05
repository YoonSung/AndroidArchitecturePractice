package yoon.architree.org.todo.tasks;

import android.support.annotation.NonNull;

import yoon.architree.org.todo.data.Task;
import yoon.architree.org.todo.data.source.TasksRepository;

/**
 * Listens to user actions from the UI ({@link TasksFragment}), 
 * retrieves the data and updates the UI as required
 */
public class TasksPresenter implements TasksContract.Presenter {
    public TasksPresenter(TasksRepository repository, TasksContract.View view) {
        
        view.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        
    }

    @Override
    public void loadTasks(boolean forceUpdate) {

    }

    @Override
    public void addNewTask() {

    }

    @Override
    public void openTaskDetails(@NonNull Task requestedTask) {

    }

    @Override
    public void completeTask(@NonNull Task completedTask) {

    }

    @Override
    public void activateTask(@NonNull Task activeTask) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void setFiltering(TasksFilterType requestType) {

    }

    @Override
    public TasksFilterType getFiltering() {
        return null;
    }

    @Override
    public void start() {

    }
}
