package yoon.architree.org.todo.tasks;

import yoon.architree.org.todo.data.Task;

public interface TaskItemListener {

    void onTaskClick(Task clickedTask);

    void onCompleteTaskClick(Task completedTask);

    void onActivateTaskClick(Task activatedTask);
}
