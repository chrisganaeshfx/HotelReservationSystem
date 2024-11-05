package managementclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;


public class SystemAdministration {
    
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private Scanner scanner = new Scanner(System.in);
    private int choice = 0;

    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** System Administration Module ***");
            System.out.println("You are login as " + currentEmployee.getUsername() + " with " + currentEmployee.getRole().toString() + " rights\n");
            System.out.println("1: Create New Employee");
            System.out.println("2: View All Employees");
            System.out.println("3: Create New Partner");
            System.out.println("4: View All Partners");
            System.out.println("5: Back");
            choice = 0; 
            
            while(choice < 1 || choice > 5) {
                System.out.print("> ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        // Pseudocode for creating a new employee
                        System.out.println("Creating new employee...");
                        break;
                    case 2:
                        // Pseudocode for viewing all employees
                        System.out.println("Viewing all employees...");
                        break;
                    case 3:
                        // Pseudocode for creating a new partner
                        System.out.println("Creating new partner...");
                        break;
                    case 4:
                        // Pseudocode for viewing all partners
                        System.out.println("Viewing all partners...");
                        break;
                    case 5:
                        return; // Go back to the main menu
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        }
    }
}
