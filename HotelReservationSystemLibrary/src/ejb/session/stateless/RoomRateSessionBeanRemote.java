/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.RoomRate;
import java.util.List;
import javax.ejb.Remote;
import util.enums.RoomRateTypeEnum;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomrate.DeleteRoomRateException;
import util.exceptions.roomrate.InvalidRoomRateUpdateException;
import util.exceptions.roomrate.NonUniqueRoomRatesFoundException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomrate.RoomRateNotFoundException;

@Remote
public interface RoomRateSessionBeanRemote {
    
    public Long createNewRoomRate(RoomRate newRoomRate) throws RoomRateExistException, UnknownPersistenceException;

    public RoomRate retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException;

    public List<RoomRate> retrieveAllEnabledRoomRates();

    public void updateRoomRate(RoomRate updatedRoomRate) throws RoomRateNotFoundException, InvalidRoomRateUpdateException;

    public boolean isRoomRateUsedByRoomTypeId(Long roomTypeId, RoomRateTypeEnum rateType);

    public void disableRoomRate(Long roomRateId) throws RoomRateNotFoundException;

    public void deleteRoomRate(Long roomRateId) throws RoomRateNotFoundException, DeleteRoomRateException;

    public List<RoomRate> retrieveMultipleRoomRatesByRoomTypeIdAndRateTypeEnum(Long roomTypeId, RoomRateTypeEnum rateType);

    public RoomRate retrieveSingleRoomRateByRoomTypeIdAndRateTypeEnum(Long roomTypeId, RoomRateTypeEnum rateType) throws RoomRateNotFoundException, NonUniqueRoomRatesFoundException;

}
