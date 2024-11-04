/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.Customer;
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
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public CustomerSessionBean() {
    }

    @Override
    public Long createNewCustomer(Customer newCustomer) throws GuestExistException, UnknownPersistenceException {
        try {
            em.persist(newCustomer);
            em.flush();
            return newCustomer.getGuestId();
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
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT g FROM Guest g");
        return query.getResultList();
    }

    @Override
    public Customer retrieveCustomerByGuestId(Long guestId) throws GuestNotFoundException {
        Customer guest = em.find(Customer.class, guestId);
        if (guest == null) {
            throw new GuestNotFoundException("Customer with Guest ID " + guestId + " does not exist!");
        }
        return guest;
    }

    @Override
    public Customer retrieveCustomerByGuestUsername(String username) throws GuestNotFoundException {
        try {
            Query query = em.createQuery("SELECT g FROM Guest g WHERE g.username = :inUsername");
            query.setParameter("inUsername", username);
            return (Customer) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new GuestNotFoundException("Guest with GuestUsername " + username + " does not exist!");
        }
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) throws GuestNotFoundException, InvalidGuestUpdateException {
        if (updatedCustomer != null && updatedCustomer.getGuestId() != null) {
            Customer guestToUpdate = retrieveCustomerByGuestId(updatedCustomer.getGuestId());

            if (guestToUpdate == null) {
                throw new GuestNotFoundException("Customer to update not found with Guest ID: " + updatedCustomer.getGuestId());
            }
            guestToUpdate.setUsername(updatedCustomer.getUsername());
            guestToUpdate.setPassword(updatedCustomer.getPassword());
            guestToUpdate.setName(updatedCustomer.getName());
            guestToUpdate.setEmail(updatedCustomer.getEmail());

        } else {
            throw new InvalidGuestUpdateException("Customer information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteCustomer(Long guestId) throws GuestNotFoundException, DeleteGuestException {
        Customer guestToRemove = retrieveCustomerByGuestId(guestId);
        try {
            em.remove(guestToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteGuestException("Unable to delete customer with Guest ID " + guestId);
        }
    }

    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer guest = retrieveCustomerByGuestUsername(username);
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
