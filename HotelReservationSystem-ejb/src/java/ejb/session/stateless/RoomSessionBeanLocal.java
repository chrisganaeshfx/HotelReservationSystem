/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */

package ejb.session.stateless;

import entity.Room;
import java.util.List;
import javax.ejb.Local;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.DeleteRoomException;
import util.exceptions.room.InvalidRoomUpdateException;
import util.exceptions.room.RoomExistException;
import util.exceptions.room.RoomNotFoundException;

@Local
public interface RoomSessionBeanLocal {

    public Long createNewRoom(Room newRoom) throws RoomExistException, UnknownPersistenceException;

    public Room retrieveRoomById(Long roomId) throws RoomNotFoundException;

    public Room retrieveRoomByRoomNumber(String roomNumber) throws RoomNotFoundException;

    public List<Room> retrieveAllEnabledRooms();

    public List<Room> retrieveEnabledRoomsByRoomTypeId(Long roomTypeId);

    public List<Room> retrieveEnabledAndAvailableRoomsByRoomTypeId(Long roomTypeId);

    public void updateRoom(Room updatedRoom) throws RoomNotFoundException, InvalidRoomUpdateException;

    public boolean isRoomUsed(Long roomId);

    public void disableRoom(Long roomId) throws RoomNotFoundException;

    public void deleteRoom(Long roomId) throws RoomNotFoundException, DeleteRoomException;

    public List<Room> retrieveAllRooms();

}
