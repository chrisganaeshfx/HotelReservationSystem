package managementclient;

import entity.Employee;
import java.util.Scanner;

public class FrontOffice {
    
    private Scanner scanner = new Scanner(System.in);
    private int choice = 0;

    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Front Office Module ***");
            System.out.println("You are login as " + currentEmployee.getUsername() + " with " + currentEmployee.getRole().toString() + " rights\n");
            System.out.println("1: Walk-in Search Room");
            /*
            To be placed under (1)
            System.out.println("1: Walk-in Reserve Room");
            */
            System.out.println("2: Check-in Guest");
            System.out.println("3: Check-out Guest");
            System.out.println("4: Back");
            choice = 0;
            
            while(choice < 1 || choice > 4) {
                
                System.out.print("> ");
                int choice = scanner.nextInt();
                scanner.nextLine();
            
                switch (choice) {
                    case 1:
                        System.out.println("Searching available room for walk-in...");
                        break;
                    case 2:
                        System.out.println("Checking in guest...");
                        break;
                    case 3:
                        System.out.println("Checking out guest...");
                        break;
                    case 4:
                        return; // Go back to the main menu
                    default:
                        System.out.println("Invalid option, please try again!");
            }

            }
        }
    }
}
