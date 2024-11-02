/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Room;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.DeleteRoomException;
import util.exceptions.room.InvalidRoomUpdateException;
import util.exceptions.room.RoomNotFoundException;
import util.exceptions.room.RoomExistException;

@Remote
public interface RoomSessionBeanRemote {
    public Long createNewRoom(Room newRoom) throws RoomExistException, UnknownPersistenceException;

    public List<Room> retrieveAllRooms();

    public Room retrieveRoomById(Long roomId) throws RoomNotFoundException;

    public Room retrieveRoomByRoomNumber(int roomNumber) throws RoomNotFoundException;

    public void updateRoom(Room updatedRoom) throws RoomNotFoundException, InvalidRoomUpdateException;

    public void deleteRoom(Long roomId) throws RoomNotFoundException, DeleteRoomException;
}
