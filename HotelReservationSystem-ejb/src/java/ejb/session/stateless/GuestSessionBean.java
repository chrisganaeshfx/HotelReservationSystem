/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.Guest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.general.InvalidLoginCredentialException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.guest.DeleteGuestException;
import util.exceptions.guest.GuestNotFoundException;
import util.exceptions.guest.GuestExistException;
import util.exceptions.guest.InvalidGuestUpdateException;

@Stateless
public class GuestSessionBean implements GuestSessionBeanRemote, GuestSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public GuestSessionBean() {
    }

    @Override
    public Long createNewGuest(Guest newGuest) throws GuestExistException, UnknownPersistenceException {
        try {
            em.persist(newGuest);
            em.flush();
            return newGuest.getCustomerId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new GuestExistException("Username already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Guest> retrieveAllGuests() {
        Query query = em.createQuery("SELECT g FROM Guest g");
        return query.getResultList();
    }

    @Override
    public Guest retrieveGuestById(Long guestId) throws GuestNotFoundException {
        Guest guest = em.find(Guest.class, guestId);
        if (guest == null) {
            throw new GuestNotFoundException("Guest ID " + guestId + " does not exist!");
        }
        return guest;
    }

    @Override
    public Guest retrieveGuestByUsername(String username) throws GuestNotFoundException {
        try {
            Query query = em.createQuery("SELECT g FROM Guest g WHERE g.username = :inUsername");
            query.setParameter("inUsername", username);
            return (Guest) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new GuestNotFoundException("Guest with username " + username + " does not exist!");
        }
    }

    @Override
    public void updateGuest(Guest updatedGuest) throws GuestNotFoundException, InvalidGuestUpdateException {
        if (updatedGuest != null && updatedGuest.getCustomerId() != null) {
            Guest guestToUpdate = retrieveGuestById(updatedGuest.getCustomerId());

            if (guestToUpdate == null) {
                throw new GuestNotFoundException("Guest to update not found with ID: " + updatedGuest.getCustomerId());
            }
            guestToUpdate.setUsername(updatedGuest.getUsername());
            guestToUpdate.setPassword(updatedGuest.getPassword());
            guestToUpdate.setName(updatedGuest.getName());
            guestToUpdate.setEmail(updatedGuest.getEmail());

        } else {
            throw new InvalidGuestUpdateException("Guest information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteGuest(Long guestId) throws GuestNotFoundException, DeleteGuestException {
        Guest guestToRemove = retrieveGuestById(guestId);
        try {
            em.remove(guestToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteGuestException("Unable to delete guest with ID " + guestId);
        }
    }

    @Override
    public Guest guestLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Guest guest = retrieveGuestByUsername(username);
            if (guest.getPassword().equals(password)) {
                return guest;
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password.");
            }
        } catch (GuestNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid username or password.");
        }
    }

    

}
