package org.example;

import org.example.enums.Priority;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoDAOTest {
    private TodoDAO todoDAO;

    @BeforeAll
    void setup() {
        todoDAO = new TodoDAO();
    }

    @Test
    @DisplayName("Test: Save a Task")
    void testSaveTask() {
        TodoItem item = new TodoItem();
        item.setTodo("Test");
        item.setDueDate(0);
        item.setPriority(Priority.HIGH);

        todoDAO.saveTodo(item);

        TodoItem retrieved = todoDAO.getTodo(item.id);
        assertNotNull(retrieved);
        assertEquals(retrieved.toString(), item.toString());
    }

    @Test
    @DisplayName("Test: Get All")
    void testGetAll() {
        var items = todoDAO.getAllTodo();

        assertNotNull(items);
        assertFalse(items.isEmpty());
    }

    @Test
    @DisplayName("Test: Delete Todo")
    void testDeleteTodo() {
        TodoItem item = new TodoItem();
        item.setTodo("Test");
        item.setDueDate(0);
        item.setPriority(Priority.HIGH);

        todoDAO.saveTodo(item);
        todoDAO.deleteTodo(item.id);
        var retrieve = todoDAO.getTodo(item.id);

        assertNull(retrieve);
    }
}
