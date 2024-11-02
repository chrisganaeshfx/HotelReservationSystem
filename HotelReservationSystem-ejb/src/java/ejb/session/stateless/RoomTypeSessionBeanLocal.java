/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import entity.RoomType;
import java.util.List;
import javax.ejb.Local;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.DeleteRoomException;
import util.exceptions.room.InvalidRoomUpdateException;
import util.exceptions.room.RoomNotFoundException;
import util.exceptions.room.RoomExistException;
import util.exceptions.roomtype.DeleteRoomTypeException;
import util.exceptions.roomtype.InvalidRoomTypeUpdateException;
import util.exceptions.roomtype.RoomTypeExistException;
import util.exceptions.roomtype.RoomTypeNotFoundException;

@Local
public interface RoomTypeSessionBeanLocal {

    public Long createNewRoomType(RoomType newRoomType) throws RoomTypeExistException, UnknownPersistenceException;

    public List<RoomType> retrieveAllRoomTypes();

    public RoomType retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException;

    public void updateRoomType(RoomType updatedRoomType) throws RoomTypeNotFoundException, InvalidRoomTypeUpdateException;

    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException, DeleteRoomTypeException;
    
}
