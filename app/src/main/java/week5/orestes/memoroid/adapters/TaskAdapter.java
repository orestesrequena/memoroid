package week5.orestes.memoroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.task_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.task_line_title);
            viewHolder.description = convertView.findViewById(R.id.task_line_description);
            viewHolder.deadline = convertView.findViewById(R.id.task_line_deadline);
            viewHolder.background = convertView.findViewById(R.id.task_line_background);
            viewHolder.done = convertView.findViewById(R.id.task_line_done);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task task = getItem(position);
        viewHolder.title.setText(task.getTitle());
        viewHolder.description.setText(task.getDescription());

        viewHolder.done.setVisibility(task.isDone() ? View.VISIBLE : View.GONE);
        switch (task.getPriority()){
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
        return convertView;
    }

    private class ViewHolder{
        TextView title, description, deadline;
        ImageView done;
        View background;

    }
}
