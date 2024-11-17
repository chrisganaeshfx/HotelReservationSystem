package managementclient;

import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;
import javax.ejb.EJB;

public class SalesOperation {
    
    @EJB
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    
    private Scanner scanner = new Scanner(System.in);
    private int response = 0;

    public SalesOperation() {
    }

    public SalesOperation(
            RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, 
            RoomRateSessionBeanRemote roomRateSessionBeanRemote) {
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
    }

    
    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Hotel Operation Module ***");
            System.out.println("You are login as '" + currentEmployee.getUsername() + "' with " + currentEmployee.getRole().toString() + " rights!\n");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View All Room Rates");
            System.out.println("3: View Room Rate Details");
            System.out.println("4: Back");
            response = 0;

            while(response < 1 || response > 4) {
                System.out.print("> ");
                int response = scanner.nextInt(); 

                if(response == 1) {
                    System.out.println("\n*** Hotel Operation :: Create New Room Rate ***");
                    createNewRoomRate();
                }
                else if(response == 2) {
                    System.out.println("\n*** Hotel Operation :: View All Room Rates ***");
                    viewAllRoomRates();
                    scanner.nextLine();
                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();  
                    
                }
                else if(response == 3) {
                    System.out.println("\n*** Hotel Operation :: View Room Rate Details ***");
                    viewAllRoomRates();
                    viewRoomRateDetails();
                } 
                else if(response == 4) {
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

    private void createNewRoomRate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllRoomRates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewRoomRateDetails() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}