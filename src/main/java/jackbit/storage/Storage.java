package jackbit.storage;

import jackbit.task.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Storage class handles loading and saving tasks to a file.
 */
public class Storage {
    private Path filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws IOException If an I/O error occurs.
     */
    public ArrayList<Task> load() throws IOException {
        if (!Files.exists(filePath)) {
            // If the file does not exist, create an empty file and return an empty list
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(filePath);
        ArrayList<Task> taskList = new ArrayList<>(lines.size());

        for (String line : lines) {
            Task task = Task.toTask(line);
            taskList.add(task);
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
        FileWriter fileWriter = new FileWriter(filePath.toFile());

        for (Task task : taskList) {
            fileWriter.write(task.toString() + "\n");
        }

        fileWriter.close();
    }
}