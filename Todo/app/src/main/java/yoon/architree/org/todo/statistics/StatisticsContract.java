package yoon.architree.org.todo.statistics;

import yoon.architree.org.todo.BasePresenter;
import yoon.architree.org.todo.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface StatisticsContract {
    
    interface View extends BaseView<Presenter> {

        void setProgressIndicator(boolean active);

        void showStatistics(int numberOfIncompleteTasks, int numberOfCompletedTasks);

        void showLoadingStatisticsError();

        boolean isActive();
    }
    
    interface Presenter extends BasePresenter {
        
    }
}
