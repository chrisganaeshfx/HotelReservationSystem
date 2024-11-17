package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomtype.DeleteRoomTypeException;
import util.exceptions.roomtype.InvalidRoomTypeUpdateException;
import util.exceptions.roomtype.RoomTypeExistException;
import util.exceptions.roomtype.RoomTypeNotFoundException;
import util.helper.ClientHelper;

@Stateless
public class RoomTypeSessionBean implements RoomTypeSessionBeanRemote, RoomTypeSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public RoomTypeSessionBean() {
    }

    @Override
    public Long createNewRoomType(RoomType newRoomType) throws RoomTypeExistException, UnknownPersistenceException {
        try {
            em.persist(newRoomType);
            em.flush();
            Long newRoomTypeId = newRoomType.getRoomTypeId();
            System.out.println("Successfully created " + newRoomType.getName().toString() + " RoomType with Id: " + newRoomTypeId + "!\n");
            return newRoomTypeId;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new RoomTypeExistException("Room type name already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public List<RoomType> retrieveAllRoomTypes() {
        Query query = em.createQuery("SELECT rt FROM RoomType rt ORDER BY rt.roomTypeId ASC");
        List<RoomType> enabledRoomTypes = query.getResultList();
        for (RoomType rt : enabledRoomTypes) {
            rt.getRoomRates().size();
        }
        return enabledRoomTypes;
    }
    
    @Override
    public List<RoomType> retrieveAllEnabledRoomTypes() {
        Query query = em.createQuery("SELECT rt FROM RoomType rt WHERE rt.isEnabled = true ORDER BY rt.roomTypeId ASC");
        List<RoomType> enabledRoomTypes = query.getResultList();
        for (RoomType rt : enabledRoomTypes) {
            rt.getRoomRates().size();
        }
        return enabledRoomTypes;
    }
    
    /*
    @Override
    public List<String> retrieveAllEnabledRoomTypeNames() {
        Query query = em.createQuery("SELECT rt.name FROM RoomType rt WHERE rt.isEnabled = true ORDER BY rt.roomTypeId ASC");
        return query.getResultList();
    }
    */
    
    @Override
    public RoomType retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException {
        RoomType roomType = em.find(RoomType.class, roomTypeId);
        if (roomType == null) {
            throw new RoomTypeNotFoundException("Room type ID " + roomTypeId + " does not exist!");
        }
        roomType.getRoomRates().size();
        return roomType;
    }

    @Override
    public void updateRoomType(RoomType updatedRoomType) throws RoomTypeNotFoundException, InvalidRoomTypeUpdateException {
        if (updatedRoomType != null && updatedRoomType.getRoomTypeId() != null) {
            RoomType roomTypeToUpdate = retrieveRoomTypeById(updatedRoomType.getRoomTypeId());
            if (roomTypeToUpdate == null) {
                throw new RoomTypeNotFoundException("Room type to update not found with ID: " + updatedRoomType.getRoomTypeId());
            }

            roomTypeToUpdate.setName(updatedRoomType.getName());
            roomTypeToUpdate.setNextHighestRoomTypeId(updatedRoomType.getNextHighestRoomTypeId());
            roomTypeToUpdate.setDescription(updatedRoomType.getDescription());
            roomTypeToUpdate.setSize(updatedRoomType.getSize());
            roomTypeToUpdate.setBed(updatedRoomType.getBed());
            roomTypeToUpdate.setCapacity(updatedRoomType.getCapacity());
            roomTypeToUpdate.setAmenities(updatedRoomType.getAmenities());
            roomTypeToUpdate.setRoomRates(updatedRoomType.getRoomRates());
            System.out.println("Successfully updated RoomType with Id: " + roomTypeToUpdate.getRoomTypeId() + "!\n");

        } else {
            throw new InvalidRoomTypeUpdateException("Room type information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public boolean isRoomTypeUsed(String roomTypeName) {
        Query query = em.createQuery("SELECT rm FROM Room rm WHERE rm.roomType = :inRoomTypeName");
        query.setParameter("inRoomTypeName", roomTypeName);
        List<Room> roomsUsingRoomType = query.getResultList();
        if(roomsUsingRoomType.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public void disableRoomType(Long roomTypeId) throws RoomTypeNotFoundException {
        RoomType roomTypeToDisable = retrieveRoomTypeById(roomTypeId);
        roomTypeToDisable.setIsEnabled(Boolean.FALSE);
    }
    
    
    @Override
    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException, DeleteRoomTypeException {
        RoomType roomTypeToRemove = retrieveRoomTypeById(roomTypeId);
        try {
            em.remove(roomTypeToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteRoomTypeException("Unable to delete room type with ID " + roomTypeId);
        }
    }
}