package ejb.session.stateless;

import entity.Reservation;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.reservation.DeleteReservationException;
import util.exceptions.reservation.InvalidReservationException;
import util.exceptions.reservation.ReservationExistsException;
import util.exceptions.reservation.ReservationNotFoundException;

@Remote
public interface ReservationSessionBeanRemote {

    public Long createNewReservation(Reservation newReservation) throws ReservationExistsException, UnknownPersistenceException, InvalidReservationException;

    public List<Reservation> retrieveAllReservations();

    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException;

    public void updateReservation(Reservation updatedReservation) throws ReservationNotFoundException, InvalidReservationException;

    public void deleteReservation(Long reservationId) throws ReservationNotFoundException, DeleteReservationException;
}
