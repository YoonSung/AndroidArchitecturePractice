package yoon.architree.org.todo.taskdetail;

import yoon.architree.org.todo.BasePresenter;
import yoon.architree.org.todo.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TaskDetailContract {
    
    interface View extends BaseView<Presenter> {
        
        void setLoadingIndicator(boolean active);

        void showMissingTask();

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

        void showCompletionStatus(boolean complete);

        void showEditTask(String taskId);

        void showTaskDeleted();

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        boolean isActive();
    }
    
    interface Presenter extends BasePresenter {

        void editTask();

        void deleteTask();

        void completeTask();

        void activateTask();
    }
}
