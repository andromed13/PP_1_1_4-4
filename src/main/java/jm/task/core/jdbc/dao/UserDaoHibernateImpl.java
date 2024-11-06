package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session = Util.getSessionFactory().openSession();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS userss " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = session.beginTransaction();
        try {
        String sql = "DROP TABLE IF EXISTS userss";
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            } e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем — " +name+ " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        try {
            String hql = "DELETE FROM User WHERE id = :id";
            Query query = session.createQuery(hql);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = session.beginTransaction();
        List <User> users = new ArrayList<>();
        try {
            String hql = "FROM User";
            Query query = session.createQuery(hql, User.class);
            users = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = session.beginTransaction();
        String hql = "DELETE FROM User ";
        Query query = session.createQuery(hql);
        query.executeUpdate();
        transaction.commit();
    }
}
