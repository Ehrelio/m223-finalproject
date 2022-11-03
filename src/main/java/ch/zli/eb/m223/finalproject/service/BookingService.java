package ch.zli.eb.m223.finalproject.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.eb.m223.finalproject.model.Booking;

@ApplicationScoped
public class BookingService {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Booking createBooking(Booking booking){
        return entityManager.merge(booking);
    }

    @Transactional
    public Booking updateBooking(Long id, Booking booking){
        booking.setId(id);
        return entityManager.merge(booking);
    }

    @Transactional
    public void deleteBooking(Long id){
        var entity = entityManager.find(Booking.class, id);
        entityManager.remove(entity);
    }
}
