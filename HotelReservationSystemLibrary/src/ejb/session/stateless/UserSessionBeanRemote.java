/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.Customer;
import entity.Employee;
import entity.Guest;
import entity.Partner;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.customer.CustomerExistException;
import util.exceptions.customer.CustomerNotFoundException;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.employee.EmployeeNotFoundException;
import util.exceptions.general.InvalidLoginCredentialException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.guest.GuestExistException;
import util.exceptions.guest.GuestNotFoundException;
import util.exceptions.partner.PartnerExistException;
import util.exceptions.partner.PartnerNotFoundException;

@Remote
public interface UserSessionBeanRemote {
    public Long createNewEmployee(Employee newEmployee) throws EmployeeExistException, UnknownPersistenceException;

    public List<Employee> retrieveAllEmployees();

    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;

    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException;

    public Long createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException;

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomerByGuestId(Long guestId) throws CustomerNotFoundException;

    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException;

    public Long createNewGuest(Guest newGuest) throws GuestExistException, UnknownPersistenceException;

    public List<Guest> retrieveAllGuests();

    public List<String> retrieveAllGuestUsernames();

    public Guest retrieveGuestByGuestId(Long guestId) throws GuestNotFoundException;

    public Guest retrieveGuestByUsername(String username) throws GuestNotFoundException;

    public Long convertGuestToCustomer(Long guestId, String name, String email);

    public Guest guestLogin(String username, String password) throws InvalidLoginCredentialException;

    public Long createNewPartner(Partner newPartner) throws PartnerExistException, UnknownPersistenceException;

    public List<Partner> retrieveAllPartners();

    public List<String> retrieveAllPartnerUsernames();

    public Partner retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException;

    public Partner retrievePartnerByUsername(String username) throws PartnerNotFoundException;

    public Partner partnerLogin(String username, String password) throws InvalidLoginCredentialException;
}
