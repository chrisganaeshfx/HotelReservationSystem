package managementclient;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.ExceptionReportSessionBeanRemote;
// Need to import Guest Session Bean
import ejb.session.stateless.ReservationSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import javax.ejb.EJB;


public class Main {

    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    @EJB
    private static ExceptionReportSessionBeanRemote exceptionReportSessionBeanRemote;
    @EJB
    private static ReservationSessionBeanRemote reservationSessionBeanRemote;
    @EJB
    private static RoomSessionBeanRemote roomSessionBeanRemote;
    @EJB
    private static RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private static RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(customerSessionBeanRemote, employeeSessionBeanRemote, exceptionReportSessionBeanRemote, reservationSessionBeanRemote, roomSessionBeanRemote, roomRateSessionBeanRemote, roomTypeSessionBeanRemote);
        mainApp.runApp();
    }
    
}
