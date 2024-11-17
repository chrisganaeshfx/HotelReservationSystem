package ejb.session.stateless;

import entity.Reservation;
import java.util.Date;
import java.util.List;
import java.util.Scanner;  // Import Scanner here
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.reservation.InvalidReservationException;
import util.exceptions.reservation.ReservationExistsException;
import util.exceptions.reservation.ReservationNotFoundException;

/**
 *
 * @author chrisganaeshfxavier
 */
@Stateful
public class SearchAndReserveSessionBean implements SearchAndReserveSessionBeanRemote, SearchAndReserveSessionBeanLocal {

    @EJB
    private RoomTypeSessionBeanLocal roomTypeSessionBeanLocal;
    @EJB
    private RoomSessionBeanLocal roomSessionBeanLocal;
    @EJB
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;
    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;
    
    private Scanner scanner = new Scanner(System.in);  // Instantiate Scanner here
    private Date searchCID = new Date();
    private Date searchCOD = new Date();
    private int searchNumRooms = 0;
    private int response = 0;

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    // Reservation methods 
    
    @Override
    public Long createNewReservation(Reservation newReservation) throws ReservationExistsException, UnknownPersistenceException, InvalidReservationException {
        try {
            em.persist(newReservation);
            em.flush();
            Long newReservationId = newReservation.getReservationId();
            System.out.println("Successfully created Reservation with Id: " + newReservationId + "!\n");
            return newReservationId;
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
    public List<Reservation> retrieveReservationsByRoomTypeId(String roomTypeId) {
        Query query = em.createQuery("SELECT res FROM Reservation res JOIN res.roomType rt WHERE rt.roomTypeId = :inRoomTypeId");
        query.setParameter("inRoomTypeId", roomTypeId);
        List<Reservation> reservations = query.getResultList();
        for (Reservation res : reservations) {
            res.getAllocatedRooms().size();
            res.getRoomType();
            res.getCustomer();
        }
        return reservations;
    }

    @Override
    public List<Reservation> retrieveAllReservations() {
        Query query = em.createQuery("SELECT r FROM Reservation r ORDER BY r.reservationId ASC");
        List<Reservation> reservations = query.getResultList();
        for (Reservation res : reservations) {
            res.getAllocatedRooms().size();
            res.getRoomType();
            res.getCustomer();
        }
        return reservations;
    }
    
    @Override
    public List<Reservation> retrieveReservationsByCheckInDate(Date checkInDate) {
        Query query = em.createQuery("SELECT res FROM Reservation res WHERE res.checkInDate = :inCheckInDate ORDER BY rr.roomRateId ASC");
        query.setParameter("inCheckInDate", checkInDate);
        List<Reservation> reservations = query.getResultList();
        for (Reservation res : reservations) {
            res.getAllocatedRooms().size();
            res.getRoomType();
            res.getCustomer();
        }
        return reservations;
    }

    @Override
    public Reservation retrieveReservationById(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = em.find(Reservation.class, reservationId);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist!");
        }
        return reservation;
    }
}
