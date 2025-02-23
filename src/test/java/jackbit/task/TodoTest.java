package jackbit.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void testToString() {
        Todo todo = new Todo("something 1");
        assertEquals("[T][ ] something 1", todo.toString(), "Unmarked Todo Task not created properly");
    }

    @Test
    public void testMarkAndUnmark() {
        Todo todo = new Todo("Read a book");
        todo.mark();
        assertEquals("[T][X] Read a book", todo.toString(), "Not marked properly");

        todo.unmark();
        assertEquals("[T][ ] Read a book", todo.toString(), "Not unmarked properly");
    }
}
