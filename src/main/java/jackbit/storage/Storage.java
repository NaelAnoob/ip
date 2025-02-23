package jackbit.storage;

import jackbit.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Storage class handles loading and saving tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws FileNotFoundException If the file does not exist.
     */
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

    /**
     * Saves tasks to the file.
     *
     * @param taskList The list of tasks to be saved.
     * @throws IOException If an I/O error occurs.
     */
    public void save(ArrayList<Task> taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);

        for (Task task : taskList) {
            fileWriter.write(task.toString() + "\n");
        }

        fileWriter.close();
    }
}