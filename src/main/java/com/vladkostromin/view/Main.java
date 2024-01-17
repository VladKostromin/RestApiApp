package com.vladkostromin.view;

import com.vladkostromin.model.Event;
import com.vladkostromin.repository.EventRepository;
import com.vladkostromin.repository.HibernateImpl.EventHibernateImpl;
import com.vladkostromin.service.EventService;

public class Main {

    private static final EventRepository eventRepository = new EventHibernateImpl();

    private static final EventService eventService = new EventService(eventRepository);
    public static void main(String[] args) {
        Event event = eventService.getEventById(10);
        System.out.println(event);

    }
}
