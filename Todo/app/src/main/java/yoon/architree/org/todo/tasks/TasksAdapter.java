package yoon.architree.org.todo.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.List;

import yoon.architree.org.todo.R;
import yoon.architree.org.todo.data.Task;

/**
 * Created by rhyno on 9/5/16.
 */
public class TasksAdapter extends BaseAdapter {
    private List<Task> mTasks;
    private final TaskItemListener mItemListener;

    public TasksAdapter(List<Task> tasks, TaskItemListener itemListener) {
        setList(tasks);
        mItemListener = itemListener;
    }

    private void setList(List<Task> tasks) {
        mTasks = Preconditions.checkNotNull(tasks);
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Task getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
        }
        
        final Task task = getItem(position);
        
        TextView titleTextView = (TextView) rowView.findViewById(R.id.title);
        titleTextView.setText(task.getTitleForList());

        CheckBox completeCheckBox = (CheckBox) rowView.findViewById(R.id.complete);

        // Active/completed task UI
        completeCheckBox.setChecked(task.isCompleted());
        if (task.isCompleted()) {
            rowView.setBackgroundDrawable(viewGroup.getContext()
                    .getResources().getDrawable(R.drawable.list_completed_touch_feedback));
        } else {
            rowView.setBackgroundDrawable(viewGroup.getContext()
                    .getResources().getDrawable(R.drawable.touch_feedback));
        }

        completeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!task.isCompleted()) {
                    mItemListener.onCompleteTaskClick(task);
                } else {
                    mItemListener.onActivateTaskClick(task);
                }
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onTaskClick(task);
            }
        });

        return rowView;
    }

    public void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }
}
