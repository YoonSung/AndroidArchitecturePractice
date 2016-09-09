package yoon.architree.org.todo.addedittask;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import yoon.architree.org.todo.Injection;
import yoon.architree.org.todo.R;
import yoon.architree.org.todo.util.ActivityUtils;

public class AddEditTaskActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
        
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        
        AddEditTaskFragment addEditTaskFragment = (AddEditTaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        
        String taskId = null;
        if (addEditTaskFragment == null) {
            addEditTaskFragment = AddEditTaskFragment.newInstance();
            
            if (getIntent().hasExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID)) {
                taskId = getIntent().getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID);
                actionBar.setTitle(R.string.edit_task);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
            } else {
                actionBar.setTitle(R.string.add_task);
            }

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    addEditTaskFragment,
                    R.id.contentFrame
            );
        }
        
        // Create the presenter
        new AddEditTaskPresenter(
                taskId,
                Injection.provideTasksRepository(getApplicationContext()),
                addEditTaskFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    
}
