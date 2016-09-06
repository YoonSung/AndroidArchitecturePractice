package yoon.architree.org.todo;

import android.content.Context;

import yoon.architree.org.todo.data.source.TasksRepository;
import yoon.architree.org.todo.data.source.local.TasksLocalDataSource;
import yoon.architree.org.todo.data.source.remote.TasksRemoteDataSource;

public class Injection {
    
    private static TasksRepository tasksRepository;
    
    public static TasksRepository provideTasksRepository(Context applicationContext) {
        if (tasksRepository == null) {
            tasksRepository = TasksRepository.getInstance(TasksRemoteDataSource.getInstance(), TasksLocalDataSource.getInstance(applicationContext));
        }
        return tasksRepository;
    }
}
