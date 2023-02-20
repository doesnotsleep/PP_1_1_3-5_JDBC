package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users" +
                    "(id MEDIUMINT not null auto_increment," +
                    "name VARCHAR(50)," +
                    "lastname VARCHAR(50)," +
                    "age TINYINT,"
                    + "PRIMARY KEY (id))").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            User users = new User(name, lastName, age);
            session.save(users);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
            System.out.println("User с ID – " + id + " удален из базы данных");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> users = new ArrayList<>();
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try (SessionFactory factory = getSessionFactory()) {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}
