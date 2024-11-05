package managementclient;

import ejb.session.stateless.ExceptionReportSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;

public class HotelOperation {
    
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;
    private ExceptionReportSessionBeanRemote exceptionReportSessionBeanRemote;
    
    private Scanner scanner = new Scanner(System.in);
    private int choice = 0;

    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Hotel Operation Module ***");
            System.out.println("You are login as " + currentEmployee.getUsername() + " with " + currentEmployee.getRole().toString() + " rights\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            /*
            To be placed under (2)
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            */
            System.out.println("3: View All Room Types");
            System.out.println("4: Create New Room");
            System.out.println("5: Update Room");
            System.out.println("6: Delete Room");
            System.out.println("7: View All Rooms");
            System.out.println("8: View Room Allocation Exception Report");
            System.out.println("9: Back");
            choice = 0;

            while (choice < 1 || choice > 9) {
                
                System.out.print("> ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
            }
            
            switch (choice) {
                case 1:
                    System.out.println("Creating new room type...");
                    break;
                case 2:
                    System.out.println("Viewing room type details...");
                    break;
                case 3:
                    System.out.println("Viewing all room types...");
                    break;
                case 4:
                    System.out.println("Creating new room...");
                    break;
                case 5:
                    System.out.println("Updating room...");
                    break;
                case 6:
                    System.out.println("Deleting room...");
                    break;
                case 7:
                    System.out.println("Viewing all rooms...");
                    break;
                case 8:
                    System.out.println("Viewing room allocation exception report...");
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid option, please try again!");
            }
        }
    }
}
