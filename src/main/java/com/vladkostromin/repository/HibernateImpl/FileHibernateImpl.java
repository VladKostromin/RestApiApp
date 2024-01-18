package com.vladkostromin.repository.HibernateImpl;

import com.vladkostromin.model.File;
import com.vladkostromin.repository.FileRepository;
import com.vladkostromin.util.HibernateUtils;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class FileHibernateImpl implements FileRepository {
    @Override
    public File findById(Integer id) {
        try(Session session = HibernateUtils.getSession()) {
            return session.find(File.class, id);
        }
    }

    @Override
    public List<File> getAll() {
        try(Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM File", File.class).list();
        }
    }

    @Override
    public File save(File file) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(file);
                transaction.commit();
                return file;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }

        }
    }

    @Override
    public File update(File file) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(file);
                transaction.commit();
                return file;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public File deleteById(Integer id) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            File file = findById(id);
            try {
                session.remove(file);
                transaction.commit();
                return file;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
