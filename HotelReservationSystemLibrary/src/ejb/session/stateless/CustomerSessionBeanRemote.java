/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.general.InvalidLoginCredentialException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.guest.DeleteGuestException;
import util.exceptions.guest.GuestNotFoundException;
import util.exceptions.guest.GuestExistException;
import util.exceptions.guest.InvalidGuestUpdateException;

@Remote
public interface CustomerSessionBeanRemote {

    public Long createNewCustomer(Customer newGuest) throws GuestExistException, UnknownPersistenceException;

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomerByGuestId(Long guestId) throws GuestNotFoundException;

    public Customer retrieveCustomerByGuestUsername(String username) throws GuestNotFoundException;

    public void updateCustomer(Customer guestToUpdate) throws GuestNotFoundException, InvalidGuestUpdateException;

    public void deleteCustomer(Long guestId) throws GuestNotFoundException, DeleteGuestException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;
}
