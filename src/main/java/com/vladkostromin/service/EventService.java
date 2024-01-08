package com.vladkostromin.service;

import com.vladkostromin.model.Event;
import com.vladkostromin.repository.EventRepository;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event deleteEvent(Integer id) {
        return eventRepository.deleteById(id);
    }
}
