package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TodoDAO {
    public void saveTodo(TodoItem todo) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(todo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public TodoItem getTodo(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(TodoItem.class, id);
        }
    }

    public List<TodoItem> getAllTodo() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from TodoItem").getResultList();
        }
    }

    public void updateTodo(TodoItem todo) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(todo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTodo(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            var item = session.find(TodoItem.class, id);
            if (item != null) {
                session.remove(item);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}