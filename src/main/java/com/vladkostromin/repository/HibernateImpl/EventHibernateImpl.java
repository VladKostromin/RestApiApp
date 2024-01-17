package com.vladkostromin.repository.HibernateImpl;

import com.vladkostromin.model.Event;
import com.vladkostromin.repository.EventRepository;
import com.vladkostromin.util.HibernateUtils;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EventHibernateImpl implements EventRepository {

    private static final String HQL = "SELECT e FROM Event e JOIN FETCH e.user JOIN FETCH e.file WHERE e.id = :eventId";
    @Override
    public Event findById(Integer id) {
        try(Session session = HibernateUtils.getSession()) {
            Event event = session.createQuery(HQL, Event.class).setParameter("eventId", id).uniqueResult();
            Hibernate.initialize(event.getUser());
            return event;
        }
    }

    @Override
    public List<Event> getAll() {
        try(Session session = HibernateUtils.getSession()) {
            return session.createQuery("FROM Event", Event.class).list();
        }
    }

    @Override
    public Event save(Event event) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(event);
                transaction.commit();
                return event;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public Event update(Event event) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(event);
                transaction.commit();
                return event;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public Event deleteById(Integer id) {
        try(Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            Event eventToDelete = session.createQuery(HQL, Event.class).setParameter("eventId", id).uniqueResult();
            try {
                session.remove(eventToDelete);
                transaction.commit();
                return eventToDelete;
            } catch (Throwable e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
