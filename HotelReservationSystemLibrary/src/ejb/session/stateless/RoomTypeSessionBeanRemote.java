/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.RoomRate;
import entity.RoomType;
import java.util.List;
import javax.ejb.Remote;
import util.enums.RoomRateTypeEnum;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomrate.DeleteRoomRateException;
import util.exceptions.roomrate.InvalidRoomRateUpdateException;
import util.exceptions.roomrate.NonUniqueRoomRatesFoundException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomrate.RoomRateNotFoundException;
import util.exceptions.roomtype.DeleteRoomTypeException;
import util.exceptions.roomtype.InvalidRoomTypeUpdateException;
import util.exceptions.roomtype.RoomTypeExistException;
import util.exceptions.roomtype.RoomTypeNotFoundException;

@Remote
public interface RoomTypeSessionBeanRemote {

    public Long createNewRoomType(RoomType newRoomType) throws RoomTypeExistException, UnknownPersistenceException;
    
    public List<RoomType> retrieveAllRoomTypes();

    public List<RoomType> retrieveAllEnabledRoomTypes();

    public RoomType retrieveRoomTypeById(Long roomTypeId) throws RoomTypeNotFoundException;

    public void updateRoomType(RoomType updatedRoomType) throws RoomTypeNotFoundException, InvalidRoomTypeUpdateException;

    public boolean isRoomTypeUsed(String roomTypeName);

    public void disableRoomType(Long roomTypeId) throws RoomTypeNotFoundException;

    public void deleteRoomType(Long roomTypeId) throws RoomTypeNotFoundException, DeleteRoomTypeException;
}
