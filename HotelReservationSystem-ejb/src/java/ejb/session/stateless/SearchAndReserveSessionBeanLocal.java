/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */

package ejb.session.stateless;

import entity.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.reservation.InvalidReservationException;
import util.exceptions.reservation.ReservationExistsException;
import util.exceptions.reservation.ReservationNotFoundException;

@Local
public interface SearchAndReserveSessionBeanLocal {

    // Reservation Methods 
    
    public Long createNewReservation(Reservation newReservation) throws ReservationExistsException, UnknownPersistenceException, InvalidReservationException;

    public List<Reservation> retrieveReservationsByRoomTypeId(String roomTypeId);

    public List<Reservation> retrieveAllReservations();

    public List<Reservation> retrieveReservationsByCheckInDate(Date checkInDate);

    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    // Search Methods 
    
    
    
}
