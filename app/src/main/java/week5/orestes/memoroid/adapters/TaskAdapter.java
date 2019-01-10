package week5.orestes.memoroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.util.Date;

import week5.orestes.memoroid.R;
import week5.orestes.memoroid.managers.TaskManager;
import week5.orestes.memoroid.model.Task;

import static week5.orestes.memoroid.R.color.red;

public class TaskAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public TaskAdapter(LayoutInflater mLayoutInflater, Context mContext) {
        this.mLayoutInflater = mLayoutInflater;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return TaskManager.getInstance().getTaskSize();
    }

    @Override
    public Task getItem(int position) {
        return TaskManager.getInstance().getTaskForPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.task_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.task_line_title);
            viewHolder.description = convertView.findViewById(R.id.task_line_description);
            viewHolder.deadline = convertView.findViewById(R.id.task_line_deadline);
            viewHolder.background = convertView.findViewById(R.id.task_line_background);
            viewHolder.done = convertView.findViewById(R.id.task_line_done);
            viewHolder.doneAction = convertView.findViewById(R.id.left_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Task task = getItem(position);
        viewHolder.title.setText(task.getTitle());
        viewHolder.description.setText(task.getDescription());

        viewHolder.done.setVisibility(task.isDone() ? View.VISIBLE : View.GONE);
        switch (task.getPriority()) {
            case 1:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
                break;
            case 2:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.orange));
                break;
            case 3:
                viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
                break;
        }
        if (task.getDeadLine() != 0) {
            PrettyTime p = new PrettyTime();
            viewHolder.deadline.setText(p.format(new Date(task.getDeadLine())));
        }
        SwipeLayout swipeLayout = convertView.findViewById(R.id.swipe_layout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);
        viewHolder.doneAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setDone(!task.isDone());
                notifyDataSetChanged();
                TaskManager.getInstance().save();
            }
        });
        if(task.isDone()){
            viewHolder.background.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView title, description, deadline;
        ImageView done, doneAction;
        View background;

    }
}
