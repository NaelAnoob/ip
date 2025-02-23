package jackbit.storage;

import jackbit.Jackbit;
import jackbit.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath){
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws FileNotFoundException {
        File file = new File(filePath);
        Scanner s = new Scanner(file);
        int i = 1;
        ArrayList<Task> taskList = new ArrayList<>(100);

        while (s.hasNext()) {

            Task task = Task.toTask(s.nextLine());
            System.out.println(i + ". " + task.toString());
            taskList.add(task);
            i++;
        }

        return taskList;
    }

    public void save(ArrayList<Task> taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);

        for (Task task : taskList) {
            fileWriter.write(task.toString() + "\n");
        }

        fileWriter.close();

    }
}

//END OF STORAGE//
