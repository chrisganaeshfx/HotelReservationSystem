/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.Guest;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.general.InvalidLoginCredentialException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.guest.DeleteGuestException;
import util.exceptions.guest.GuestNotFoundException;
import util.exceptions.guest.GuestExistException;
import util.exceptions.guest.InvalidGuestUpdateException;

@Remote
public interface GuestSessionBeanRemote {

    public Long createNewGuest(Guest newGuest) throws GuestExistException, UnknownPersistenceException;

    public List<Guest> retrieveAllGuests();

    public Guest retrieveGuestById(Long guestId) throws GuestNotFoundException;

    public Guest retrieveGuestByUsername(String username) throws GuestNotFoundException;

    public void updateGuest(Guest guestToUpdate) throws GuestNotFoundException, InvalidGuestUpdateException;

    public void deleteGuest(Long guestId) throws GuestNotFoundException, DeleteGuestException;

    public Guest guestLogin(String username, String password) throws InvalidLoginCredentialException;
}
