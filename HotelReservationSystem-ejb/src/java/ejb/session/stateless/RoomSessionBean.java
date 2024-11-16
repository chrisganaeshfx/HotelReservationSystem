package ejb.session.stateless;

import entity.Reservation;
import entity.Room;
import entity.RoomType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.DeleteRoomException;
import util.exceptions.room.InvalidRoomUpdateException;
import util.exceptions.room.RoomNotFoundException;
import util.exceptions.room.RoomExistException;

@Stateless
public class RoomSessionBean implements RoomSessionBeanRemote, RoomSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public RoomSessionBean() {
    }

    @Override
    public Long createNewRoom(Room newRoom) throws RoomExistException, UnknownPersistenceException {
        try {
            em.persist(newRoom);
            em.flush();
            Long newRoomId = newRoom.getRoomId();
            String roomType = newRoom.getRoomType().getName();
            String roomNumber = newRoom.getRoomNumber();
            System.out.println("Successfully created " + roomType + " Room with Number: " + roomNumber + " and Id: " + newRoomId + "!\n");
            return newRoomId;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new RoomExistException("Room number already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public Room retrieveRoomById(Long roomId) throws RoomNotFoundException {
        Room room = em.find(Room.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist!");
        }
        room.getRoomType();
        return room;
    }
    
    @Override
    public Room retrieveRoomByRoomNumber(String roomNumber) throws RoomNotFoundException {
        try {
            Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :inRoomNumber");
            query.setParameter("inRoomNumber", roomNumber);
            Room room = (Room) query.getSingleResult();
            room.getRoomType();
            return room;
        } catch (javax.persistence.NoResultException ex) {
            throw new RoomNotFoundException("Room with room number " + roomNumber + " does not exist!");
        }
    }
    
    @Override
    public List<Room> retrieveAllEnabledRooms() {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.isEnabled = true ORDER BY r.roomType, r.roomId ASC");
        List<Room> rooms = query.getResultList();
        for(Room r : rooms) {
            r.getRoomType();
        }
        return rooms;
    }

    @Override
    public List<Room> retrieveEnabledRoomsByRoomTypeId(Long roomTypeId) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.isEnabled = true AND r.roomType.roomTypeId = :inRoomTypeId ORDER BY r.roomId ASC");
        query.setParameter("roomTypeId", roomTypeId);
        List<Room> rooms = query.getResultList();
        for(Room r : rooms) {
            r.getRoomType();
        }
        return rooms;
    }
    
    @Override
    public List<Room> retrieveEnabledAndAvailableRoomsByRoomTypeId(Long roomTypeId) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomType.roomTypeId = :inRoomTypeId AND r.isEnabled = true AND r.isAvailable = true ORDER BY r.roomId ASC");
        query.setParameter("inRoomTypeId", roomTypeId);
        List<Room> rooms = query.getResultList();
        for(Room r : rooms) {
            r.getRoomType();
        }
        return rooms;
    }

    @Override
    public void updateRoom(Room updatedRoom) throws RoomNotFoundException, InvalidRoomUpdateException {
        if (updatedRoom != null && updatedRoom.getRoomId() != null) {
            Room roomToUpdate = retrieveRoomById(updatedRoom.getRoomId());
            if (roomToUpdate == null) {
                throw new RoomNotFoundException("Room to update not found with ID: " + updatedRoom.getRoomId());
            }

            roomToUpdate.setFloor(updatedRoom.getFloor());
            roomToUpdate.setUnit(updatedRoom.getUnit());
            roomToUpdate.setRoomType(updatedRoom.getRoomType());
            roomToUpdate.setIsAvailable(updatedRoom.getIsAvailable());
            roomToUpdate.setIsEnabled(updatedRoom.getIsEnabled());
            System.out.println("Successfully updated Room with Id: " + updatedRoom.getRoomId() + "!\n");
        } else {
            throw new InvalidRoomUpdateException("Room information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public boolean isRoomUsed(Long roomId) {
        Query query = em.createQuery("SELECT res FROM Reservation res JOIN res.allocatedRooms rm WHERE rm.roomId = :inRoomId");
        query.setParameter("inRoomId", roomId);
        List<Reservation> reservationsUsingRoom = query.getResultList();
        if(reservationsUsingRoom.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public void disableRoom(Long roomId) throws RoomNotFoundException {
        Room roomToDisable = retrieveRoomById(roomId);
        roomToDisable.setIsEnabled(Boolean.FALSE);
    }
    
    
    @Override
    public void deleteRoom(Long roomId) throws RoomNotFoundException, DeleteRoomException {
        Room roomToRemove = retrieveRoomById(roomId);
        try {
            em.remove(roomToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteRoomException("Unable to delete room with ID " + roomId);
        }
    }

}