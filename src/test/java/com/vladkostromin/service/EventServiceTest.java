package com.vladkostromin.service;

import com.vladkostromin.model.Event;
import com.vladkostromin.model.File;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private EventService eventService;
    private  Event event;
    @BeforeEach
    public void setUpEvent() {
        event = new Event(1,new User(1, "John", new ArrayList<>()), new File(1, "john_file", "com/test/java"));
    }

    @Test
    public void testGetEvent_Found() {
        when(eventRepository.findById(event.getId())).thenReturn(event);
        Event resultEvent = eventService.getEventById(event.getId());
        Assertions.assertEquals(event, resultEvent);
        verify(eventRepository).findById(event.getId());

    }

    @Test
    public void testGetEventById_NotFound() {
        when(eventRepository.findById(event.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.getEventById(event.getId()));
    }

    @Test
    public void testCreateEvent() {
        when(eventRepository.save(event)).thenReturn(event);
        Event savedEvent = eventService.saveEvent(event);
        Assertions.assertEquals(event, savedEvent);
        verify(eventRepository).save(event);
    }

    @Test
    public void testUpdateEvent_Found() {
        when(eventRepository.update(event)).thenReturn(event);
        Event updatedEvent = eventService.updateEvent(event);
        Assertions.assertEquals(event, updatedEvent);
        verify(eventRepository).update(event);
    }

    @Test
    public void testUpdateEvent_NotFound() {
        when(eventRepository.update(event)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> eventService.updateEvent(event));
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = List.of(event, event, event, event);
        when(eventRepository.getAll()).thenReturn(events);
        List<Event> resultList = eventService.getAllEvents();
        Assertions.assertEquals(events, resultList);
        verify(eventRepository).getAll();
    }

    @Test
    public void testDeleteEvent() {
        when(eventRepository.deleteById(event.getId())).thenReturn(event);
        Event deletedEvent = eventService.deleteEvent(event.getId());
        Assertions.assertEquals(event, deletedEvent);
        verify(eventRepository).deleteById(event.getId());
    }
    @Test
    public void testDeleteEvent_NotFound() {
        when(eventRepository.deleteById(event.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> eventService.deleteEvent(event.getId()));
    }
}
