/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package managementclient;

import ejb.session.stateless.AllocateAndExceptionReportSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.SearchAndReserveSessionBeanRemote;
import ejb.session.stateless.UserSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import javax.ejb.EJB;
import util.enums.EmployeeRoleEnum;
import util.exceptions.general.InvalidLoginCredentialException;


public class MainApp {

    @EJB
    private UserSessionBeanRemote userSessionBeanRemote;
    @EJB
    private RoomSessionBeanRemote roomSessionBeanRemote;
    @EJB
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private SearchAndReserveSessionBeanRemote searchAndReserveSessionBeanRemote;
    @EJB
    private AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote;
    
    private Scanner scanner = new Scanner(System.in);
    private int response = 0;
    private Employee currentEmployee = new Employee();
    
    public MainApp() {
    }

    public MainApp(UserSessionBeanRemote userSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, SearchAndReserveSessionBeanRemote searchAndReserveSessionBeanRemote, AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote) {
        this.userSessionBeanRemote = userSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.searchAndReserveSessionBeanRemote = searchAndReserveSessionBeanRemote;
        this.allocateAndExceptionReportSessionBeanRemote = allocateAndExceptionReportSessionBeanRemote;
    }
    
    
    public void runApp() {

        while(true) {
            System.out.println("\n*** Hotel Management System ***");
            System.out.println("1: Login");
            System.out.println("2: Exit"); 
            response = 0;
            
            while (response < 1 || response > 2) {    
                System.out.print("> ");
                response = scanner.nextInt();
                
                if(response == 1) {
                    System.out.println("\n*** Hotel Management System :: Employee Login ***");
                    try {
                        currentEmployee = userSessionBeanRemote.employeeLogin();
                        System.out.println("Login successful!");
                        showMenu();
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } 
                else if(response == 2) {
                    break;
                } 
                else {
                    System.out.println("Invalid option, please try again!\n");
                }   
            }
            
            if(response == 2) {
                break;
            }
        }
    }
    
    public void showMenu() {
        EmployeeRoleEnum currentRole = currentEmployee.getRole();

        switch (currentRole) {
            case SYSTEM_ADMINISTRATOR:
                SystemAdministration systemAdministration = new SystemAdministration(employeeSessionBeanRemote);
                systemAdministration.showMenu(currentEmployee);
                break;
            case OPERATION_MANAGER:
                HotelOperation hotelOperation = new HotelOperation(
                        roomTypeSessionBeanRemote,
                        roomRateSessionBeanRemote,
                        roomSessionBeanRemote,
                        allocateAndExceptionReportSessionBeanRemote);
                hotelOperation.showMenu(currentEmployee);
                break;
            case SALES_MANAGER:
                SalesOperation salesOperation = new SalesOperation(
                    roomTypeSessionBeanRemote,
                    roomRateSessionBeanRemote);
                salesOperation.showMenu(currentEmployee);
                break;
            case GUEST_RELATION_OFFICER:
                FrontOffice frontOffice = new FrontOffice(
                    userSessionBeanRemote,
                    searchAndReserveSessionBeanRemote);
                frontOffice.showMenu(currentEmployee);
                break;
            default:
                System.out.println("Invalid role.");
        }
    }
    
    
    
    
}
