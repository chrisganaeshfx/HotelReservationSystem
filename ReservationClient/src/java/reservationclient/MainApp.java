package reservationclient;

import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.ReservationSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import java.util.Scanner;
import javax.ejb.EJB;

public class MainApp {
    
    private static RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private static RoomSessionBeanRemote roomSessionBeanRemote;
    private static RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private static ReservationSessionBeanRemote reservationSessionBeanRemote;
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    public MainApp() {
    }
    
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, ReservationSessionBeanRemote reservationSessionBeanRemote, RoomRateSessionBeanRemote roomRateSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote) {
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("Welcome to the Hotel Reservation System");
            System.out.println("1: Login");
            System.out.println("2: Register an account");
            System.out.println("3: Continue without an account");
            System.out.println("4: Exit\n");i 
            response = 0;
            
            while(response < 1 || response > 3) {
                
                System.out.print("> ");
                
                response = scanner.nextInt();
                
                if(response == 1)
                {
                    
                }
            }
        }
    }
    
    private void doLogin() throws InvalidLoginCredentialException {}
    
}
