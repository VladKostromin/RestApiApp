package com.vladkostromin.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory () {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Unable to obtain session " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private static SessionFactory getSessionFactory() {
        
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
