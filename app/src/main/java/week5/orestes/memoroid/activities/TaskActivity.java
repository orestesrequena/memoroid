package week5.orestes.memoroid.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import info.hoang8f.android.segmented.SegmentedGroup;
import week5.orestes.memoroid.R;
import week5.orestes.memoroid.managers.TaskManager;
import week5.orestes.memoroid.model.Task;

public class TaskActivity extends AppCompatActivity {
    EditText title, description;
    SegmentedGroup priority;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.task_title);
        priority = findViewById(R.id.task_priority);
        description = findViewById(R.id.task_description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_task_save) {
            String sTitle = title.getText().toString();
            String sDescription = description.getText().toString();
            int iPriority = getPriority();
            Task task = new Task(sTitle,iPriority);
            task.setDescription(sDescription);
            TaskManager.getInstance().addNewTask(task);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getPriority(){
        if(priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_1).getId()){
            return 1;
        }else if(priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_2).getId()){
            return 2;
        }else {
            return 3;
        }

    }

}
