package managementclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.ExceptionReportSessionBeanRemote;
// Need to import Guest Session Bean
import ejb.session.stateless.ReservationSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import util.enums.EmployeeRoleEnum;
import util.exceptions.general.InvalidLoginCredentialException;

public class MainApp {

    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private ExceptionReportSessionBeanRemote exceptionReportSessionBeanRemote;
    private ReservationSessionBeanRemote reservationSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    
    //Other attributes
    private Scanner scanner = new Scanner(System.in);
    private int choice = 0;
    private Employee currentEmployee = null;
    
    //No-argument Constructor
    public MainApp() {
    }

    //Full Constructor
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, EmployeeSessionBeanRemote employeeSessionBeanRemote, ExceptionReportSessionBeanRemote exceptionReportSessionBeanRemote, ReservationSessionBeanRemote reservationSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.exceptionReportSessionBeanRemote = exceptionReportSessionBeanRemote;
        this.reservationSessionBeanRemote = reservationSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
    }
    
    //Main method
    public void runApp() {

        while(true) {
            System.out.println("Welcome to the Hotel Management System");
            
            if(currentEmployee == null) {
                System.out.print("1: Login");
                System.out.print("2: Exit\n");   
            } else {
                System.out.print("1: Logout");
                System.out.print("2: Exit\n");
            }
            choice = 0;
            
            while (choice < 1 || choice > 2) {    
                System.out.println("> ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                if(currentEmployee == null) {
                    switch(choice) {
                        case 1:
                            try {
                                login();
                                System.out.println("Login successful!");
                                showMenu();
                            } catch(InvalidLoginCredentialException ex) {
                                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                            }  
                            break;
                        case 2:
                            System.out.println("Exiting system...");
                            return;
                        default:
                            System.out.println("Invalid option, please try again!\n");
                    } 
                } else {
                    switch (choice) {
                        case 1:
                            logout();
                            System.out.println("Logout successful!");
                            break;
                        case 2:
                            System.out.println("Exiting system...");
                            return;
                        default: 
                            System.out.println("Invalid option, please try again!\n");
                    }
                    
                    if (choice == 1) {
                        break;
                    }
                }   
            }
        }
    }
    
    public void login() throws InvalidLoginCredentialException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0)
        {
            // currentEmployee = EmployeeSessionBeanRemote.employeeLogin(username, password);      
        }
        else
        {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }
    
    public void showMenu() {
        EmployeeRoleEnum currentRole = currentEmployee.getRole();
        
        
        switch (currentRole) {
            case SYSTEM_ADMINISTRATOR:
                SystemAdministration.showMenu(currentEmployee);
                break;
            case OPERATION_MANAGER:
                HotelOperation.showMenu(currentEmployee);
                break;
            case SALES_MANAGER:
                SalesOperation.showMenu(currentEmployee);
                break;
            case GUEST_RELATION_OFFICER:
                FrontOffice.showMenu(currentEmployee);
                break;
            default:
                System.out.println("Invalid role.");
                logout();
        }
    }
    
    public void logout() {
        System.out.println("Logout successful.");
        currentEmployee = null;
    }
    
}