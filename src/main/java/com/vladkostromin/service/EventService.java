package com.vladkostromin.service;

import com.vladkostromin.model.Event;
import com.vladkostromin.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(Integer id) {
        Event event = eventRepository.findById(id);
        if(event == null) throw new EntityNotFoundException("event with " + id + " not found");
        return event;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        Event updatedEvent = eventRepository.update(event);
        if(updatedEvent == null) throw new EntityNotFoundException("event with " + event.getId() + " not found");
        return updatedEvent;
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event deleteEvent(Integer id) {
        Event eventToDelete = eventRepository.deleteById(id);
        if(eventToDelete == null) throw new EntityNotFoundException("Event with " + id + " not found");
        return eventToDelete;
    }
}
