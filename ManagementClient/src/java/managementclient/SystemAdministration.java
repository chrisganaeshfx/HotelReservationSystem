package managementclient;

import ejb.session.stateless.UserSessionBeanRemote;
import entity.Employee;
import entity.Partner;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.enums.EmployeeRoleEnum;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.partner.PartnerExistException;


public class SystemAdministration {
    
    @EJB
    private UserSessionBeanRemote userSessionBeanRemote;
    private Scanner scanner = new Scanner(System.in);
    private int response = 0;

    public SystemAdministration() {
    }

    public SystemAdministration(UserSessionBeanRemote userSessionBeanRemote) {
        this.userSessionBeanRemote = userSessionBeanRemote;
    }

    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** System Administration Module ***");
            System.out.println("You are login as '" + currentEmployee.getUsername() + "' with " + currentEmployee.getRole().toString() + " rights!\n");
            
            System.out.println("1: Create New Employee");
            System.out.println("2: View All Employees");
            System.out.println("3: Create New Partner");
            System.out.println("4: View All Partners");
            System.out.println("5: Logout");
            response = 0;
                
            while(response < 1 || response > 5) {
                System.out.print("> ");
                response = scanner.nextInt();
                
                if(response == 1) {
                    System.out.println("\n*** System Administration :: Create Employee ***");
                    createNewEmployee();
                }
                else if(response == 2) {
                    System.out.println("\n*** System Administration :: View All Employees ***");
                    viewAllEmployees();
                }
                else if(response == 3) {
                    createNewPartner();
                } 
                else if(response == 4) {
                    viewAllPartners();
                } 
                else if(response == 5) {
                    break;
                }
                else {
                    System.out.println("Invalid option. Try again.");
                }
            }
            
            if(response == 5) {
                break;
            }
        }
    }

    private Employee inputEmployeeAttributes(Employee newEmployee) {
        scanner.nextLine();
        System.out.print("Enter username> ");
        newEmployee.setUsername(scanner.nextLine().trim());
        System.out.print("Enter password> ");
        newEmployee.setPassword(scanner.nextLine().trim());
        System.out.println("\n- Employee Roles -");
        System.out.println("1: System Administrator");
        System.out.println("2: Operation Manager");
        System.out.println("3: Sales Manager");
        System.out.println("4: Guest Relation Officer");
        response = 0;
        
        while(response < 1 || response > 4) {
            System.out.print("Enter Employee Role option> ");
            response = scanner.nextInt();
            
            if (response == 1) {
                newEmployee.setRole(EmployeeRoleEnum.SYSTEM_ADMINISTRATOR);
            }
            else if(response == 2) {
                newEmployee.setRole(EmployeeRoleEnum.OPERATION_MANAGER);
            }
            else if(response == 3) {
                newEmployee.setRole(EmployeeRoleEnum.SALES_MANAGER);
            }
            else if(response == 4) {
                newEmployee.setRole(EmployeeRoleEnum.GUEST_RELATION_OFFICER);
            }
            else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
        
        return newEmployee;
    }
    
    private void createNewEmployee() {
        Employee newEmployee = new Employee();
        newEmployee = inputEmployeeAttributes(newEmployee);
        
        try {
            Long newEmployeeId = userSessionBeanRemote.createNewEmployee(newEmployee);
            System.out.println("Successfully created " + newEmployee.getRole() + " " + newEmployee.getUsername() + " with Id: " + newEmployeeId + "!\n");
        }
        catch(EmployeeExistException | UnknownPersistenceException ex) {
            System.out.println("Error occured: " + ex.getMessage() + "\n");
        } 
    }

    private void viewAllEmployees() {
        System.out.println("--------------------------------------------------------");
        List<Employee> allEmployees = userSessionBeanRemote.retrieveAllEmployees();
        System.out.printf("%11s%20s%25s\n", "Employee ID", "Username", "Role");
        for(Employee e : allEmployees)
        {
            System.out.printf("%11s%20s%25s\n", e.getEmployeeId().toString(), e.getUsername(), e.getRole().toString());
        }
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
        scanner.nextLine();
    }

    
    public Partner inputPartnerAttributes (Partner partner) {
        String username = "";
        String password = "";
        String confirmPassword = "...";
        // to ensure confirmPassword and password are mismatched for the while loop
        scanner.nextLine();
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();;
        partner.setUsername(username);

        while(!password.equals(confirmPassword)) {
            System.out.print("Enter password> ");
            password = scanner.nextLine().trim();
            System.out.print("Re-enter password to confirm> ");
            confirmPassword = scanner.nextLine().trim();
            
            if (password.equals(confirmPassword)) {
                partner.setPassword(password);
            } else {
                System.out.println("Password mismatch, please try again!");
            }
        }
        return partner;
    }
    
    private void createNewPartner() {
        Partner newPartner = new Partner();
        newPartner = inputPartnerAttributes(newPartner);
        
        try {
            Long newPartnerId = userSessionBeanRemote.createNewPartner(newPartner);
            System.out.println("Successfully created " + " " + newPartner.getUsername() + " with Id: " + newPartnerId + "!\n");
        }
        catch(PartnerExistException | UnknownPersistenceException ex) {
            System.out.println("Error occured: " + ex.getMessage() + "\n");
        } 
    }

    private void viewAllPartners() {
        System.out.println("--------------------------------------------------------");
        List<Partner> allPartners = userSessionBeanRemote.retrieveAllPartners();
        System.out.printf("%11s%20s\n", "Partner ID", "Username");
        for(Partner p : allPartners)
        {
            System.out.printf("%11s%20s\n", p.getPartnerId().toString(), p.getUsername());
        }
        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
        scanner.nextLine();
    }
}