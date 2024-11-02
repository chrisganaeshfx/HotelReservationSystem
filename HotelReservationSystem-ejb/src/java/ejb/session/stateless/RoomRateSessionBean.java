package ejb.session.stateless;

import entity.RoomRate;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomrate.DeleteRoomRateException;
import util.exceptions.roomrate.InvalidRoomRateUpdateException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomrate.RoomRateNotFoundException;

@Stateless
public class RoomRateSessionBean implements RoomRateSessionBeanRemote, RoomRateSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewRoomRate(RoomRate newRoomRate) throws RoomRateExistException, UnknownPersistenceException {
        try {
            em.persist(newRoomRate);
            em.flush();
            return newRoomRate.getRoomRateId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new RoomRateExistException("Room rate name already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<RoomRate> retrieveAllRoomRates() {
        Query query = em.createQuery("SELECT rr FROM RoomRate rr");
        return query.getResultList();
    }

    @Override
    public RoomRate retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException {
        RoomRate roomRate = em.find(RoomRate.class, roomRateId);
        if (roomRate == null) {
            throw new RoomRateNotFoundException("Room rate ID " + roomRateId + " does not exist!");
        }
        return roomRate;
    }

    @Override
    public void updateRoomRate(RoomRate updatedRoomRate) throws RoomRateNotFoundException, InvalidRoomRateUpdateException {
        if (updatedRoomRate != null && updatedRoomRate.getRoomRateId() != null) {
            RoomRate roomRateToUpdate = retrieveRoomRateById(updatedRoomRate.getRoomRateId());
            if (roomRateToUpdate == null) {
                throw new RoomRateNotFoundException("Room rate to update not found with ID: " + updatedRoomRate.getRoomRateId());
            }

            // Additional validations for RoomRate attributes
            if (updatedRoomRate.getRatePerNight() < 0) {
                throw new InvalidRoomRateUpdateException("Room rate per night cannot be negative.");
            }
            if (updatedRoomRate.getStartDate() != null && updatedRoomRate.getStartDate().before(new Date())) {
                throw new InvalidRoomRateUpdateException("Start date must be in the future.");
            }
            if (updatedRoomRate.getEndDate() != null && updatedRoomRate.getStartDate() != null &&
                updatedRoomRate.getEndDate().before(updatedRoomRate.getStartDate())) {
                throw new InvalidRoomRateUpdateException("End date must be after start date.");
            }

            roomRateToUpdate.setName(updatedRoomRate.getName());
            roomRateToUpdate.setRoomType(updatedRoomRate.getRoomType());
            roomRateToUpdate.setRateType(updatedRoomRate.getRateType());
            roomRateToUpdate.setRatePerNight(updatedRoomRate.getRatePerNight());
            roomRateToUpdate.setIsPromotionOrPeakRate(updatedRoomRate.getIsPromotionOrPeakRate());
            roomRateToUpdate.setStartDate(updatedRoomRate.getStartDate());
            roomRateToUpdate.setEndDate(updatedRoomRate.getEndDate());
            roomRateToUpdate.setIsEnabled(updatedRoomRate.getIsEnabled());
        } else {
            throw new InvalidRoomRateUpdateException("Room rate information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteRoomRate(Long roomRateId) throws RoomRateNotFoundException, DeleteRoomRateException {
        RoomRate roomRateToRemove = retrieveRoomRateById(roomRateId);
        try {
            em.remove(roomRateToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteRoomRateException("Unable to delete room rate with ID " + roomRateId);
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
