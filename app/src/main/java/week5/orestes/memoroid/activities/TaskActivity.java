package week5.orestes.memoroid.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.AndroidException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;

import info.hoang8f.android.segmented.SegmentedGroup;
import week5.orestes.memoroid.R;
import week5.orestes.memoroid.common.Constants;
import week5.orestes.memoroid.managers.TaskManager;
import week5.orestes.memoroid.model.Task;

public class TaskActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private EditText title, description;
    private SegmentedGroup priority;
    private boolean hasChanged;
    private Button date, time;
    private Calendar calendarDeadline;
    private Task currentTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        title = findViewById(R.id.task_title);
        priority = findViewById(R.id.task_priority);
        description = findViewById(R.id.task_description);
        date = findViewById(R.id.task_deadline_date);
        time = findViewById(R.id.task_deadline_time);

        //para anyadir el boton back en el menu superior
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().hasExtra(Constants.TASK_ID)) {
            int id = getIntent().getIntExtra(Constants.TASK_ID, 0);

            currentTask = TaskManager.getInstance().getTaskFromId(id);
            if (currentTask != null) {
                title.setText(currentTask.getTitle());
                description.setText(currentTask.getDescription());
                if (currentTask.getDeadLine() != 0) {
                    calendarDeadline = Calendar.getInstance();
                    calendarDeadline.setTimeInMillis(currentTask.getDeadLine());
                    showDeadline();
                }
                priority.check(getRadioButtonFromPriority(currentTask.getPriority()));
            }
        }
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hasChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //detecta si priority ha cambiado y llama a la funcion para cambiar de color
        priority.setOnCheckedChangeListener(this);
        //listener pour date y time - llama funcion que luego dirige a la funcion date o time
        date.setOnClickListener(this);
        time.setOnClickListener(this);


    }

    private int getRadioButtonFromPriority(int iPriority) {
        switch (iPriority) {
            case 1:
                return findViewById(R.id.task_priority_1).getId();
            case 2:
                return findViewById(R.id.task_priority_2).getId();
            default:
                return findViewById(R.id.task_priority_3).getId();
        }
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
        //gestion de botones en el menu superior
        if (id == R.id.menu_task_save) {
            String sTitle = title.getText().toString();
            if (StringUtils.isNoneBlank(sTitle)) {
                String sDescription = description.getText().toString();
                int iPriority = getPriority();
                if (currentTask == null){
                    Task task = new Task(sTitle.trim(), iPriority);
                }else {
                    currentTask.setTitle(sTitle.trim());
                    currentTask.setPriority(iPriority);
                }

                currentTask.setDescription(sDescription);
                if (calendarDeadline != null) {
                    currentTask.setDeadLine(calendarDeadline.getTimeInMillis());
                }
                TaskManager.getInstance().saveTask(currentTask);
                finish();
            } else {
                Toast.makeText(this, R.string.title_mandatory, Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == android.R.id.home) {
            //boton back menu superior
            onBackPressed();
        }else if (id == R.id.menu_task_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle((R.string.app_name)).setMessage(R.string.delete_confirmation)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TaskManager.getInstance().deleteTask(currentTask.getId());
                            finish();
                        }
                    });
            builder.create().show();

        }

        return super.onOptionsItemSelected(item);
    }

    private int getPriority() {
        if (priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_1).getId()) {
            return 1;
        } else if (priority.getCheckedRadioButtonId() == findViewById(R.id.task_priority_2).getId()) {
            return 2;
        } else {
            return 3;
        }

    }

    //esto es la gestion back del boton del telefono, no del menu superior// pedimos si esta seguro de volver atras
    @Override
    public void onBackPressed() {
        if (hasChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle(R.string.app_name).setMessage(R.string.back_task_confirmation)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.create().show();
        } else {
            super.onBackPressed();
        }

    }

    //cambia el color de los botones dependiendo de la seleccion
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (getPriority()) {
            case 1:
                priority.setTintColor(ContextCompat.getColor(this, R.color.red));
                break;
            case 2:
                priority.setTintColor(ContextCompat.getColor(this, R.color.orange));
                break;
            default:
                priority.setTintColor(ContextCompat.getColor(this, R.color.green));
                break;
        }
    }

    //if button clicked its time or date, call  function
    @Override
    public void onClick(View v) {
        if (v.getId() == time.getId()) {
            if (calendarDeadline == null) {
                calendarDeadline = Calendar.getInstance();
            }

            int hour = calendarDeadline.get(Calendar.HOUR_OF_DAY);
            int minute = calendarDeadline.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            TimePickerDialog alertDialog = new TimePickerDialog(this, this, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(this));
            alertDialog.show();

        } else if (v.getId() == date.getId()) {
            if (calendarDeadline == null) {
                calendarDeadline = Calendar.getInstance();
            }
            int year = calendarDeadline.get(Calendar.YEAR);
            int month = calendarDeadline.get(Calendar.MONTH);
            int day = calendarDeadline.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog alertDialog = new DatePickerDialog(this, this, year, month, day);
            alertDialog.show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendarDeadline.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarDeadline.set(Calendar.MINUTE, minute);
        showDeadline();
        hasChanged = true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendarDeadline.set(Calendar.YEAR, year);
        calendarDeadline.set(Calendar.MONTH, month);
        calendarDeadline.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showDeadline();
        hasChanged = true;
    }

    private void showDeadline() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        date.setText(df.format(calendarDeadline.getTime()));
        DateFormat dfTime = DateFormat.getTimeInstance(DateFormat.SHORT);
        time.setText(dfTime.format(calendarDeadline.getTime()));
    }
}
