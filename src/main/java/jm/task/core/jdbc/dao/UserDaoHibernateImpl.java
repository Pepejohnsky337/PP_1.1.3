package jm.task.core.jdbc.dao;

import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util;

    public UserDaoHibernateImpl() {
        this.util = new Util();
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session
                    .createNativeQuery("CREATE TABLE IF NOT EXISTS users ("
                            + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                            + "name VARCHAR(255) NOT NULL, "
                            + "lastName VARCHAR(255) NOT NULL, "
                            + "age TINYINT NOT NULL)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при создании таблицы", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении таблицы", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = util.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении всех пользователей", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при очистке таблицы", e);
        }
    }
}