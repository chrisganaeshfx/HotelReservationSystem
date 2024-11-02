/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.Reservation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.reservation.DeleteReservationException;
import util.exceptions.reservation.InvalidReservationException;
import util.exceptions.reservation.ReservationExistsException;
import util.exceptions.reservation.ReservationNotFoundException;

@Stateless
public class ReservationSessionBean implements ReservationSessionBeanRemote, ReservationSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewReservation(Reservation newReservation) throws ReservationExistsException, UnknownPersistenceException, InvalidReservationException {
        try {
            validateReservation(newReservation);
            em.persist(newReservation);
            em.flush();
            return newReservation.getReservationId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ReservationExistsException("Reservation already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Reservation> retrieveAllReservations() {
        Query query = em.createQuery("SELECT r FROM Reservation r");
        return query.getResultList();
    }

    @Override
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = em.find(Reservation.class, reservationId);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist!");
        }
        return reservation;
    }

    @Override
    public void updateReservation(Reservation updatedReservation) throws ReservationNotFoundException, InvalidReservationException {
        if (updatedReservation != null && updatedReservation.getReservationId() != null) {
            Reservation reservationToUpdate = retrieveReservationById(updatedReservation.getReservationId());
            if (reservationToUpdate == null) {
                throw new ReservationNotFoundException("Reservation to update not found with ID: " + updatedReservation.getReservationId());
            }

            validateReservation(updatedReservation);

            reservationToUpdate.setCustomer(updatedReservation.getCustomer());
            reservationToUpdate.setRoomType(updatedReservation.getRoomType());
            reservationToUpdate.setNumRooms(updatedReservation.getNumRooms());
            reservationToUpdate.setCheckInDate(updatedReservation.getCheckInDate());
            reservationToUpdate.setCheckOutDate(updatedReservation.getCheckOutDate());
            reservationToUpdate.setStatus(updatedReservation.getStatus());
            reservationToUpdate.setAmount(updatedReservation.getAmount());
        } else {
            throw new InvalidReservationException("Reservation information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteReservation(Long reservationId) throws ReservationNotFoundException, DeleteReservationException {
        Reservation reservationToRemove = retrieveReservationById(reservationId);
        try {
            em.remove(reservationToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteReservationException("Unable to delete reservation with ID " + reservationId);
        }
    }

    private void validateReservation(Reservation reservation) throws InvalidReservationException {
        if (reservation.getNumRooms() < 1 || reservation.getNumRooms() > 10) {
            throw new InvalidReservationException("Number of rooms must be between 1 and 10.");
        }
        if (reservation.getCheckInDate() != null && reservation.getCheckInDate().before(new Date())) {
            throw new InvalidReservationException("Check-in date must be in the future.");
        }
        if (reservation.getCheckOutDate() != null && reservation.getCheckInDate() != null &&
            reservation.getCheckOutDate().before(reservation.getCheckInDate())) {
            throw new InvalidReservationException("Check-out date must be after check-in date.");
        }
    }
}

