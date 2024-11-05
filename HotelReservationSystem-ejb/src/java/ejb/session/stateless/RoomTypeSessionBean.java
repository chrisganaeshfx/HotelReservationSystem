/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.RoomType;
import java.util.List;
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
            return newRoomType.getRoomTypeId();
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
        Query query = em.createQuery("SELECT rt FROM RoomType rt");
        return query.getResultList();
    }

    @Override
    public RoomType retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException {
        RoomType roomType = em.find(RoomType.class, roomTypeId);
        if (roomType == null) {
            throw new RoomTypeNotFoundException("Room type ID " + roomTypeId + " does not exist!");
        }
        return roomType;
    }

    @Override
    public void updateRoomType(RoomType updatedRoomType) throws RoomTypeNotFoundException, InvalidRoomTypeUpdateException {
        if (updatedRoomType != null && updatedRoomType.getRoomTypeId() != null) {
            RoomType roomTypeToUpdate = retrieveRoomTypeById(updatedRoomType.getRoomTypeId());
            if (roomTypeToUpdate == null) {
                throw new RoomTypeNotFoundException("Room type to update not found with ID: " + updatedRoomType.getRoomTypeId());
            }

            // Additional validations for RoomType attributes
            if (updatedRoomType.getSize() < 1 || updatedRoomType.getSize() > 200) {
                throw new InvalidRoomTypeUpdateException("Room size must be between 1 and 200 sqm.");
            }
            if (updatedRoomType.getCapacity() < 1 || updatedRoomType.getCapacity() > 20) {
                throw new InvalidRoomTypeUpdateException("Room capacity must be between 1 and 20 pax.");
            }

            roomTypeToUpdate.setName(updatedRoomType.getName());
            roomTypeToUpdate.setDescription(updatedRoomType.getDescription());
            roomTypeToUpdate.setSize(updatedRoomType.getSize());
            roomTypeToUpdate.setBed(updatedRoomType.getBed());
            roomTypeToUpdate.setCapacity(updatedRoomType.getCapacity());
            roomTypeToUpdate.setAmenities(updatedRoomType.getAmenities());
            roomTypeToUpdate.setRoomInventoryOverTime(updatedRoomType.getRoomInventoryOverTime());
            roomTypeToUpdate.setRoomRates(roomTypeToUpdate.getRoomRates());
        } else {
            throw new InvalidRoomTypeUpdateException("Room type information to be updated is invalid or incomplete!");
        }
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
