package week5.orestes.memoroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import week5.orestes.memoroid.R;
import week5.orestes.memoroid.adapters.TaskAdapter;
import week5.orestes.memoroid.common.Constants;
import week5.orestes.memoroid.common.WebServerint;
import week5.orestes.memoroid.managers.TaskManager;
import week5.orestes.memoroid.model.Exemple;
import week5.orestes.memoroid.model.Task;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.main_list);
        Switch switchDone = findViewById(R.id.main_switch_done);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                startActivity(intent);
            }
        });


        taskAdapter = new TaskAdapter(getLayoutInflater(), this);
        listView.setAdapter(taskAdapter);
        listView.setEmptyView(findViewById(R.id.main_list_empty));
        listView.setOnItemClickListener(this);
        switchDone.setOnCheckedChangeListener(this);

        //test exemple API request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServerint webServerint = retrofit.create(WebServerint.class);
        webServerint.getMyExample().enqueue(new Callback<Exemple>() {
            @Override
            public void onResponse(Call<Exemple> call, Response<Exemple> response) {
                Toast.makeText(getApplicationContext(), response.body().getHello(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Exemple> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task task = taskAdapter.getItem(position);
        Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
        intent.putExtra(Constants.TASK_ID, task.getId());
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TaskManager.getInstance().setShowDone(isChecked);
        taskAdapter.notifyDataSetChanged();
    }
}
