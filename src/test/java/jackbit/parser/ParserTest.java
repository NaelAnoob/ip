package jackbit.parser;

import jackbit.Jackbit;
import jackbit.tasklist.TaskList;
import jackbit.task.Todo;
import jackbit.task.Deadline;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseTodo() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser parser = new Parser(taskList);

        try {
            parser.parse("todo something 1");
        } catch (Jackbit.JackbitException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, taskList.getSize(), "TaskList should have one task.");
        assertTrue(taskList.get(0) instanceof Todo, "The added task should be an instance of Todo.");
        assertEquals("[T][ ] something 1", taskList.get(0).toString(), "The Todo task should be formatted correctly.");
    }

    @Test
    public void testParseDeadline() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser parser = new Parser(taskList);

        try {
            parser.parse("deadline meeting /by 2023-10-31");
        } catch (Jackbit.JackbitException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, taskList.getSize(), "TaskList should have one task.");
        assertTrue(taskList.get(0) instanceof Deadline, "The added task should be an instance of Deadline.");
        assertEquals("[D][ ] meeting (by: Oct 31 2023)", taskList.get(0).toString(), "The Deadline task should be formatted correctly.");
    }

    @Test
    public void testParseInvalidCommand() {
        TaskList taskList = new TaskList(new ArrayList<>());
        Parser parser = new Parser(taskList);

        Exception exception = assertThrows(Jackbit.JackbitException.class, () -> {
            parser.parse("invalid command");
        }, "Parsing an invalid command should throw a JackbitException.");

        assertEquals("First rule you learn in clown school: Random gibberish is never funny", exception.getMessage(), "Not the gibberish Exception message.");
    }
}