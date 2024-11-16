/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.Customer;
import entity.Employee;
import entity.Guest;
import entity.Partner;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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

@Stateless
public class UserSessionBean implements UserSessionBeanRemote, UserSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public UserSessionBean() {
    }
    
    @Override
    public Long createNewEmployee(Employee newEmployee) throws EmployeeExistException, UnknownPersistenceException {
        try {
            em.persist(newEmployee);
            em.flush();
            Long newEmployeeId = newEmployee.getEmployeeId();
            System.out.println("Successfully created + " + newEmployee.getUsername() + " Employee with Id " + newEmployeeId + "!\n");
            return newEmployeeId;
        } 
        catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EmployeeExistException("Username already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM Employee e ORDER BY e.employeeId ASC");
        return query.getResultList();
    }
    
    @Override
    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = em.find(Employee.class, employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee ID " + employeeId + " does not exist!");
        }
        return employee;
    }
    
    @Override
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException {
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :inUsername");
            query.setParameter("inUsername", username);
            return (Employee) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new EmployeeNotFoundException("Employee with username " + username + " does not exist!");
        }
    }
    
    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException  {
        try {
            Employee employee = retrieveEmployeeByUsername(username);
            if (employee.getPassword().equals(password)) {
                return employee;
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password.");
            }
        } catch (EmployeeNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid username or password.");
        }
    }    
    
    @Override
    public Long createNewCustomer(Customer newCustomer) throws CustomerExistException, UnknownPersistenceException {
        try {
            em.persist(newCustomer);
            em.flush();
            Long newCustomerId = newCustomer.getGuestId();
            System.out.println("Successfully created Customer " + newCustomer.getName() + " with Id " + newCustomerId + "!\n");
            return newCustomerId;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CustomerExistException("Username already exists!");
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
        Query query = em.createQuery("SELECT c FROM Customer c ORDER BY c.guestId ASC");
        return query.getResultList();
    }

    @Override
    public Customer retrieveCustomerByGuestId(Long guestId) throws CustomerNotFoundException {
        Customer guest = em.find(Customer.class, guestId);
        if (guest == null) {
            throw new CustomerNotFoundException("Customer with Guest ID " + guestId + " does not exist!");
        }
        return guest;
    }

    @Override
    public Customer retrieveCustomerByEmail(String email) throws CustomerNotFoundException {
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :inEmail ORDER BY c.guestId ASC");
            query.setParameter("inEmail", email);
            return (Customer) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new CustomerNotFoundException("Customer with email " + email + " does not exist!");
        }
    }
    
    
    @Override
    public Long createNewGuest(Guest newGuest) throws GuestExistException, UnknownPersistenceException {
        try {
            em.persist(newGuest);
            em.flush();
            return newGuest.getGuestId();
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
        Query query = em.createQuery("SELECT g FROM Guest g ORDER BY g.guestId");
        return query.getResultList();
    }
    
    @Override
    public List<String> retrieveAllGuestUsernames() {
        Query query = em.createQuery("SELECT g.username FROM Guest g ORDER BY g.guestId");
        return query.getResultList();
    }

    @Override
    public Guest retrieveGuestByGuestId(Long guestId) throws GuestNotFoundException {
        Guest guest = em.find(Guest.class, guestId);
        if (guest == null) {
            throw new GuestNotFoundException("Guest with ID " + guestId + " does not exist!");
        }
        return guest;
    }

    @Override
    public Guest retrieveGuestByUsername(String username) throws GuestNotFoundException {
        try {
            Query query = em.createQuery("SELECT g FROM Guest g WHERE g.username = :inUsername ORDER BY g.guestId");
            query.setParameter("inUsername", username);
            return (Guest) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new GuestNotFoundException("Guest with username " + username + " does not exist!");
        }
    }
    
    @Override
    public Guest guestLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Guest guest = retrieveGuestByUsername(username);
            if (guest.getPassword().equals(password)) { // Assuming Guest has a password field
                return guest;
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password.");
            }
        } catch (GuestNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid username or password.");
        }
    }
    
    @Override
    public Long convertGuestToCustomer(Long guestId, String name, String email) {
        Guest guest = em.find(Guest.class, guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Guest with ID " + guestId + " not found.");
        }
        // Create a new Customer and copy attributes
        Customer customer = new Customer();
        customer.setGuestId(guest.getGuestId());
        customer.setUsername(guest.getUsername());
        customer.setPassword(guest.getPassword());
        customer.setName(name);
        customer.setEmail(email);
        
        em.persist(customer);
        em.remove(guest);
        em.flush();
        return customer.getGuestId();
    }
    
    @Override
    public Long createNewPartner(Partner newPartner) throws PartnerExistException, UnknownPersistenceException {
        try {
            em.persist(newPartner);
            em.flush();
            return newPartner.getPartnerId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new PartnerExistException("Username already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Partner> retrieveAllPartners() {
        Query query = em.createQuery("SELECT p FROM Partner p ORDER BY p.partnerId");
        return query.getResultList();
    }

    @Override
    public List<String> retrieveAllPartnerUsernames() {
        Query query = em.createQuery("SELECT p.username FROM Partner p ORDER BY p.partnerId");
        return query.getResultList();
    }

    @Override
    public Partner retrievePartnerByPartnerId(Long partnerId) throws PartnerNotFoundException {
        Partner partner = em.find(Partner.class, partnerId);
        if (partner == null) {
            throw new PartnerNotFoundException("Partner with ID " + partnerId + " does not exist!");
        }
        return partner;
    }

    @Override
    public Partner retrievePartnerByUsername(String username) throws PartnerNotFoundException {
        try {
            Query query = em.createQuery("SELECT p FROM Partner p WHERE p.username = :inUsername ORDER BY p.partnerId");
            query.setParameter("inUsername", username);
            return (Partner) query.getSingleResult();
        } catch (javax.persistence.NoResultException ex) {
            throw new PartnerNotFoundException("Partner with username " + username + " does not exist!");
        }
    }

    @Override
    public Partner partnerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Partner partner = retrievePartnerByUsername(username);
            if (partner.getPassword().equals(password)) { // Assuming Partner has a password field
                return partner;
            } else {
                throw new InvalidLoginCredentialException("Invalid username or password.");
            }
        } catch (PartnerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Invalid username or password.");
        }
    } 
}
