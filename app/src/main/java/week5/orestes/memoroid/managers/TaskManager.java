package week5.orestes.memoroid.managers;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import week5.orestes.memoroid.common.Constants;
import week5.orestes.memoroid.model.Task;

public class TaskManager {

    private static TaskManager instance;

    private List<Task> taskList;

    public  static TaskManager getInstance(){
        if( instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    private TaskManager(){
        taskList = Hawk.get(Constants.TASK_LIST, new ArrayList<Task>());
    }

    public int getTaskSize(){
        return taskList.size();
    }
    public Task getTaskForPosition(int position){
        return taskList.get(position);
    }

    public void addNewTask(Task task){
        taskList.add(task);
        save();
    }

    public void save(){
        Hawk.put(Constants.TASK_LIST, taskList);
    }

}
