package managementclient;

import ejb.session.stateless.RoomRateSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;

public class SalesOperation {
    
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    
    private Scanner scanner = new Scanner(System.in);
    private int choice = 0;

    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Hotel Operation Module ***");
            System.out.println("You are login as " + currentEmployee.getUsername() + " with " + currentEmployee.getRole().toString() + " rights\n");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Rate Details");
            /*
            To be placed under (2)
            System.out.println("1: Update Room Rate");
            System.out.println("2: Delete Room Rate");
            */
            System.out.println("3: View All Room Rates");
            System.out.println("4: Back");
            choice = 0;

            while(choice < 1 || choice > 4) {
                System.out.print("> ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("Creating new room rate...");
                        break;
                    case 2:
                        System.out.println("Viewing room rate details...");
                        break;
                    case 3:
                        System.out.println("Viewing all room rates...");
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option, please try again!");
                }
            }
        }
    }
}
