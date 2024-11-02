/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.employee.DeleteEmployeeException;
import util.exceptions.employee.EmployeeNotFoundException;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.employee.InvalidEmployeeUpdateException;
import util.exceptions.general.InvalidLoginCredentialException;
import util.exceptions.general.UnknownPersistenceException;

@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public EmployeeSessionBean() {
    }

    @Override
    public Long createNewEmployee(Employee newEmployee) throws EmployeeExistException, UnknownPersistenceException {
        try {
            em.persist(newEmployee);
            em.flush();
            return newEmployee.getEmployeeId();
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
        Query query = em.createQuery("SELECT e FROM Employee e");
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

    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException {
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
    public void updateEmployee(Employee updatedEmployee) throws EmployeeNotFoundException, InvalidEmployeeUpdateException {
        if (updatedEmployee != null && updatedEmployee.getEmployeeId() != null) {
            Employee employeeToUpdate = retrieveEmployeeById(updatedEmployee.getEmployeeId());
            if (employeeToUpdate == null) {
                throw new EmployeeNotFoundException("Guest to update not found with ID: " + updatedEmployee.getEmployeeId());
            }
            employeeToUpdate.setUsername(updatedEmployee.getUsername());
            employeeToUpdate.setPassword(updatedEmployee.getPassword());
            employeeToUpdate.setRole(updatedEmployee.getRole());
        } else {
            throw new InvalidEmployeeUpdateException("Employee information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException, DeleteEmployeeException {
        Employee employeeToRemove = retrieveEmployeeById(employeeId);
        try {
            em.remove(employeeToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteEmployeeException("Unable to delete employee with ID " + employeeId);
        }
    }

}
