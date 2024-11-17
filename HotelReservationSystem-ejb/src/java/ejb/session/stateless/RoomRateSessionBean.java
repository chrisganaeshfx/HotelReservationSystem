/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.Reservation;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.enums.RoomRateTypeEnum;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomrate.DeleteRoomRateException;
import util.exceptions.roomrate.InvalidRoomRateUpdateException;
import util.exceptions.roomrate.NonUniqueRoomRatesFoundException;
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
            Long newRoomRateId = newRoomRate.getRoomRateId();
            String roomType = newRoomRate.getRoomType().toString();
            RoomRateTypeEnum rateType = newRoomRate.getRateType();
            System.out.println("Successfully created " + roomType.toString() + " " + rateType + " RoomRate with Id " + newRoomRateId + "!\n");
            return newRoomRateId;
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
    public RoomRate retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException {
        RoomRate roomRate = em.find(RoomRate.class, roomRateId);
        if (roomRate == null) {
            throw new RoomRateNotFoundException("Room rate ID " + roomRateId + " does not exist!");
        }
        roomRate.getRoomType();
        return roomRate;
    }
    
    @Override
    public List<RoomRate> retrieveAllEnabledRoomRates() {
        Query query = em.createQuery("SELECT rr FROM RoomRate rr WHERE rr.isEnabled = true ORDER BY rr.roomRateId ASC");
        List<RoomRate> roomRates = query.getResultList();
        for (RoomRate rt : roomRates) {
            rt.getRoomType();
        }
        return roomRates;
    }
    
    @Override
    public RoomRate retrieveSingleRoomRateByRoomTypeIdAndRateTypeEnum(Long roomTypeId, RoomRateTypeEnum rateType) throws RoomRateNotFoundException, NonUniqueRoomRatesFoundException {
        try {
            Query query = em.createQuery("SELECT rr FROM RoomRate rr JOIN rr.roomType rt WHERE rt.roomTypeId = :inRoomTypeId AND rr.rateType = :inRateType");
            query.setParameter("inRoomTypeId", roomTypeId);
            query.setParameter("inRateType", rateType);
            RoomRate roomRate = (RoomRate) query.getSingleResult();
            roomRate.getRoomType();
            return roomRate;
        } catch (javax.persistence.NoResultException ex) {
            throw new RoomRateNotFoundException("RoomRate with RoomType: " + roomTypeId + " and RateType: " + rateType + " does not exist!");
        } catch (javax.persistence.NonUniqueResultException ex) {
            throw new NonUniqueRoomRatesFoundException("Multiple RoomRates exist with RoomType: " + roomTypeId + " and RateType: " + rateType + "!");
        }
    }
    
    @Override
    public List<RoomRate> retrieveMultipleRoomRatesByRoomTypeIdAndRateTypeEnum(Long roomTypeId, RoomRateTypeEnum rateType) {
        Query query = em.createQuery("SELECT rr FROM RoomRate rr JOIN rr.roomType rt WHERE rt.roomTypeId = :inRoomTypeId AND rr.rateType = :inRateType");
        query.setParameter("inRoomTypeId", roomTypeId);
        query.setParameter("inRateType", rateType);
        List<RoomRate> roomRates = query.getResultList();
        for (RoomRate rt : roomRates) {
            rt.getRoomType();
        }
        return roomRates;
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

            roomRateToUpdate.setRoomType(updatedRoomRate.getRoomType());
            roomRateToUpdate.setRateType(updatedRoomRate.getRateType());
            roomRateToUpdate.setName(updatedRoomRate.getName());
            roomRateToUpdate.setRatePerNight(updatedRoomRate.getRatePerNight());
            roomRateToUpdate.setIsPromotionOrPeakRate(updatedRoomRate.getIsPromotionOrPeakRate());
            roomRateToUpdate.setStartDate(updatedRoomRate.getStartDate());
            roomRateToUpdate.setEndDate(updatedRoomRate.getEndDate());
            roomRateToUpdate.setIsEnabled(updatedRoomRate.getIsEnabled());
            System.out.println("Successfully updated RoomRate with Id " + updatedRoomRate.getRoomRateId() + "!\n");
        } else {
            throw new InvalidRoomRateUpdateException("Room rate information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public boolean isRoomRateUsedByRoomTypeId(Long roomTypeId, RoomRateTypeEnum rateType) {
        Query query = em.createQuery("SELECT rt FROM RoomType rt JOIN rt.roomRates rr WHERE rr.roomType = :inRoomType AND rr.rateType = :inRateType");
        query.setParameter("inRateType", rateType);
        query.setParameter("inRoomType", roomTypeId);
        List<Reservation> roomTypeUsingRoomRate = query.getResultList();
        if(roomTypeUsingRoomRate.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public void disableRoomRate(Long roomRateId) throws RoomRateNotFoundException {
        RoomRate roomRateToDisable = retrieveRoomRateById(roomRateId);
        roomRateToDisable.setIsEnabled(Boolean.FALSE);
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
    
    
}
