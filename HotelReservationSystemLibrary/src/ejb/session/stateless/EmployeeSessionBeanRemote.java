/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.employee.DeleteEmployeeException;
import util.exceptions.employee.EmployeeNotFoundException;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.employee.InvalidEmployeeUpdateException;
import util.exceptions.general.UnknownPersistenceException;

@Remote
public interface EmployeeSessionBeanRemote {
    public Long createNewEmployee(Employee newEmployee) throws EmployeeExistException, UnknownPersistenceException;

    public List<Employee> retrieveAllEmployees();

    public Employee retrieveEmployeeById(Long employeeId) throws EmployeeNotFoundException;

    public Employee retrieveEmployeeByUsername(String username) throws EmployeeNotFoundException;

    public void updateEmployee(Employee updatedEmployee) throws EmployeeNotFoundException, InvalidEmployeeUpdateException;

    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException, DeleteEmployeeException;
}
