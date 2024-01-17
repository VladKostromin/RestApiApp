package com.vladkostromin.repository.HibernateImpl;

import com.vladkostromin.model.User;
import com.vladkostromin.repository.UserRepository;
import com.vladkostromin.util.HibernateUtils;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserHibernateImpl implements UserRepository {

    private static final String GET_HQL = "FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id";
    private static final String GET_ALL_HQL = "FROM User u LEFT JOIN FETCH u.events";

    @Override
    public User findById(Integer id) {
        try (Session session = HibernateUtils.getSession()) {
            User user = session.createQuery(GET_HQL, User.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return user;
        }
    }

    @Override
    public List<User> getAll() {
        try(Session session = HibernateUtils.getSession()) {
            return session.createQuery(GET_ALL_HQL, User.class).list();
        }
    }

    @Override
    public User save(User user) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
                return user;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public User update(User user) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(user);
                transaction.commit();
                return user;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public User deleteById(Integer id) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            User user = findById(id);
            if(user == null) throw new EntityNotFoundException("User with " + id + " not found");
            try {
                session.remove(user);
                transaction.commit();
                return user;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
