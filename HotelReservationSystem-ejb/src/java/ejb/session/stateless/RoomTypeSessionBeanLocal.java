/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */

package ejb.session.stateless;

import entity.RoomType;
import java.util.List;
import javax.ejb.Local;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomtype.DeleteRoomTypeException;
import util.exceptions.roomtype.InvalidRoomTypeUpdateException;
import util.exceptions.roomtype.RoomTypeExistException;
import util.exceptions.roomtype.RoomTypeNotFoundException;

@Local
public interface RoomTypeSessionBeanLocal {

    public Long createNewRoomType(RoomType newRoomType) throws RoomTypeExistException, UnknownPersistenceException;

    public List<RoomType> retrieveAllRoomTypes();
    
    public List<RoomType> retrieveAllEnabledRoomTypes();

    public RoomType retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException;

    public void updateRoomType(RoomType updatedRoomType) throws RoomTypeNotFoundException, InvalidRoomTypeUpdateException;

    public boolean isRoomTypeUsed(String roomTypeName);

    public void disableRoomType(Long roomTypeId) throws RoomTypeNotFoundException;

    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException, DeleteRoomTypeException;


}
