/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
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
            return newRoom.getRoomId();
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
    public List<Room> retrieveAllRooms() {
        Query query = em.createQuery("SELECT r FROM Room r");
        return query.getResultList();
    }

    @Override
    public Room retrieveRoomById(Long roomId) throws RoomNotFoundException {
        Room room = em.find(Room.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException("Room ID " + roomId + " does not exist!");
        }
        return room;
    }

    @Override
    public Room retrieveRoomByRoomNumber(int roomNumber) throws RoomNotFoundException {
        try {
            Query query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :inRoomNumber");
            query.setParameter("inRoomNumber", roomNumber);
            return (Room) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new RoomNotFoundException("Room with room number " + roomNumber + " does not exist!");
        }
    }

    @Override
    public void updateRoom(Room updatedRoom) throws RoomNotFoundException, InvalidRoomUpdateException {
        if (updatedRoom != null && updatedRoom.getRoomId() != null) {
            Room roomToUpdate = retrieveRoomById(updatedRoom.getRoomId());
            if (roomToUpdate == null) {
                throw new RoomNotFoundException("Room to update not found with ID: " + updatedRoom.getRoomId());
            }

            // Additional validations for Room attributes
            if (updatedRoom.getRoomNumber() < 100 || updatedRoom.getRoomNumber() > 9999) {
                throw new InvalidRoomUpdateException("Room number must be between 100 and 9999.");
            }

            roomToUpdate.setRoomNumber(updatedRoom.getRoomNumber());
            roomToUpdate.setRoomType(updatedRoom.getRoomType());
            roomToUpdate.setIsAvailable(updatedRoom.getIsAvailable());
            roomToUpdate.setIsEnabled(updatedRoom.getIsEnabled());
        } else {
            throw new InvalidRoomUpdateException("Room information to be updated is invalid or incomplete!");
        }
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
