package managementclient;

import ejb.session.stateless.AllocateAndExceptionReportSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.SearchAndReserveSessionBeanRemote;
import ejb.session.stateless.UserSessionBeanRemote;
import entity.Employee;
import java.util.Date;
import java.util.Scanner;
import javax.ejb.EJB;

public class FrontOffice {
    
    @EJB
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private RoomSessionBeanRemote roomSessionBeanRemote;
    @EJB
    private AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote;
    @EJB
    private SearchAndReserveSessionBeanRemote searchAndReserveSessionBeanRemote;
    @EJB
    private UserSessionBeanRemote userSessionBeanRemote;
    
    private Scanner scanner = new Scanner(System.in);
    private int response = 0;
    private Date checkInDate = null;
    private Date checkOutDate = null;
    private int numRooms = 0;

    public FrontOffice() {
    }

    public FrontOffice(
            RoomTypeSessionBeanRemote roomTypeSessionBeanRemote,
            RoomRateSessionBeanRemote roomRateSessionBeanRemote,
            RoomSessionBeanRemote roomSessionBeanRemote,
            AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote,
            SearchAndReserveSessionBeanRemote searchAndReserveSessionBeanRemote,
            UserSessionBeanRemote userSessionBeanRemote) {
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.allocateAndExceptionReportSessionBeanRemote = allocateAndExceptionReportSessionBeanRemote;
        this.searchAndReserveSessionBeanRemote = searchAndReserveSessionBeanRemote;
        this.userSessionBeanRemote = userSessionBeanRemote;
    }
    
    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Front Office Module ***");
            System.out.println("You are login as '" + currentEmployee.getUsername() + "' with " + currentEmployee.getRole().toString() + " rights!\n");
            System.out.println("1: Walk-in Search Room");
            /*
            To be placed under (1)
            System.out.println("1: Walk-in Reserve Room");
            */
            System.out.println("2: Check-in Guest");
            System.out.println("3: Check-out Guest");
            System.out.println("4: Back");
            response = 0;
            
            while(response < 1 || response > 4) {
                System.out.print("> ");
                int response = scanner.nextInt();
            
                if(response == 1) {
                    System.out.println("\n*** Front Office Module :: Walk-in Search Room ***");
                    inputSearchCriteria();
                }
                else if(response == 2) {
                    System.out.println("\n*** Front Office Module :: Check-in Guest ***");
                    checkInGuest();
                }
                else if(response == 3) {
                    System.out.println("\n*** Front Office Module :: Check-out Guest ***");
                    checkOutGuest();
                }
                else if(response == 4) {
                    break;
                }
                else {
                    System.out.println("Invalid option. Please try again!");
                }
            }
            
            if(response == 4) {
                break;
            }
        }
    }

    private void inputSearchCriteria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void checkInGuest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void checkOutGuest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
