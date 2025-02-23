package jackbit.tasklist;

import jackbit.task.Task;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }


    public int size() {
        return taskList.size();
    }

    public Task get(int i) {
        return taskList.get(i);
    }

    public void remove(int i) {
        taskList.remove(i);
    }

    public void add(int index, Task task) {
        taskList.add(index, task);
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}

//END OF TASKLIST//
