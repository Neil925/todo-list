package org.example;

import java.util.Date;

import jakarta.persistence.*;
import org.example.enums.Priority;

@Entity
@Table(name = "todos")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "todo")
    String todo;

    @Column(name = "due_date")
    long dueDate;

    @Column(name = "priority")
    Priority priority;

    @Override
    public String toString() {
        String result = "";

        var now = new Date().getTime();

        result += this.todo + "\n";
        result += "ID: " + this.id + "\n";
        result += "Due in days from now: " + (int) Math.ceil((this.getDueDate() - now) / (double) 86400000) + "\n";
        result += "Priority: " + this.getPriority() + "\n";

        return result;
    }

    public void setPriority() {
        boolean invalid = true;

        do {
            try {
                invalid = false;
                System.out.print("Please set the priority ([L]ow, [M]edium, [H]igh): ");
                char priority = App.scanner.nextLine().toLowerCase().charAt(0);

                switch (priority) {
                    case 'l':
                        this.priority = Priority.LOW;
                        break;
                    case 'm':
                        this.priority = Priority.MEDIUM;
                        break;
                    case 'h':
                        this.priority = Priority.HIGH;
                        break;
                    default:
                        invalid = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid. Try again.");
                invalid = true;
            }
        } while (invalid);
    }

    public void setDueDate() {
        boolean invalid = true;

        do {
            try {
                invalid = false;

                System.out.print("How many days from now will this be due?: ");
                int days = App.scanner.nextInt();
                App.scanner.nextLine();

                long date = new Date().getTime() + ((long) days * 24 * 60 * 60 * 1000);
                setDueDate(date);
            } catch (Exception e) {
                System.out.println("Invalid. Try again.");
                invalid = true;
            }
        } while (invalid);
    }

    public void setTodo() {
        System.out.println("What is your todo item?");
        String todo = App.scanner.nextLine();

        setTodo(todo);
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
