/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.RoomRate;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.roomrate.DeleteRoomRateException;
import util.exceptions.roomrate.InvalidRoomRateUpdateException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomrate.RoomRateNotFoundException;

@Remote
public interface RoomRateSessionBeanRemote {

    public Long createNewRoomRate(RoomRate newRoomRate) throws RoomRateExistException, UnknownPersistenceException;

    public List<RoomRate> retrieveAllRoomRates();

    public RoomRate retrieveRoomRateById(Long roomRateId) throws RoomRateNotFoundException;
    
    public void updateRoomRate(RoomRate updatedRoomRate) throws RoomRateNotFoundException, InvalidRoomRateUpdateException;

    public void deleteRoomRate(Long roomRateId) throws RoomRateNotFoundException, DeleteRoomRateException;
    
}
