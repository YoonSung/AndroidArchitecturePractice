package yoon.architree.org.todo.addedittask;

import yoon.architree.org.todo.BasePresenter;
import yoon.architree.org.todo.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AddEditTaskContract {
    
    interface View extends BaseView<Presenter> {
        
        void showEmptyTaskError();
        
        void showTasksList();
        
        void setTitle(String title);
        
        void setDescription(String description);
        
        boolean isActive();
    }
    
    interface Presenter extends BasePresenter {
        
        void saveTask(String title, String description);
        
        void populateTask();
    }
}
