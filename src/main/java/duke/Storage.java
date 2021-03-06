package duke;

import duke.Exceptions.DukeException;
import duke.Exceptions.TaskTypeNotFoundException;
import duke.Tasks.Deadline;
import duke.Tasks.Event;
import duke.Tasks.Task;
import duke.Tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    private final String FILEPATH;
    private ArrayList<Task> taskList;

    public Storage(String filePath) {
        this.FILEPATH = filePath;
        taskList = new ArrayList<>();
    }

    /**
     * Loads the data from the specified FILEPATH
     * @return Returns an <code>ArrayList</code> with data from the loaded file
     * @throws DukeException If file at <code>FILEPATH</code> is not found or if incorrect Task Type in file
     */
    public ArrayList<Task> load() throws DukeException {

        File f = new File(FILEPATH);
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            Ui.showFileNotFound();
            throw new DukeException();
        }
        Ui.showLoadingData();
        while (s.hasNext()) {
            String line = s.nextLine();
            int dividerPosition;
            char taskType = line.charAt(1);
            switch (taskType) {
            case 'T':
                taskList.add(new Todo(line.substring(7)));
                break;
            case 'D':
                dividerPosition = line.indexOf("(by:");
                taskList.add(new Deadline(line.substring(7, dividerPosition - 1),
                        line.substring(dividerPosition + 5, line.length() - 1), true));
                break;
            case 'E':
                dividerPosition = line.indexOf("(at:");
                taskList.add(new Event(line.substring(7, dividerPosition - 1),
                        line.substring(dividerPosition + 5, line.length() - 1), true));
                break;
            default:
                throw new TaskTypeNotFoundException();
            }
            if (line.charAt(4) == '\u2713') {
                taskList.get(taskList.size() - 1).setDone();
            }
        }
        return taskList;
    }

    /**
     * Saves the changed <code>TaskList</code> to the file specified at <code>FILEPATH</code>
     * @param saveList <code>TaskList</code> of data to be saved
     * @throws IOException If there are errors during the saving process
     */
    public void save(TaskList saveList) throws IOException {
        taskList = saveList.getTaskList();
        File f = new File(FILEPATH);
        if (f.createNewFile()) {
            Ui.showCreateNewFile(FILEPATH);
        }
        FileWriter fw = new FileWriter(FILEPATH);

        for (Task task : taskList) {
            if (task != null) {
                fw.write(task.toString() + "\n");
            }
        }
        fw.close();
    }

}
