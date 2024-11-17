/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package managementclient;

import ejb.session.stateless.AllocateAndExceptionReportSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import ejb.session.stateless.SearchAndReserveSessionBeanRemote;
import ejb.session.stateless.UserSessionBeanRemote;
import javax.ejb.EJB;

public class Main {

    @EJB
    private static UserSessionBeanRemote userSessionBeanRemote;
    @EJB
    private static RoomSessionBeanRemote roomSessionBeanRemote;
    @EJB
    private static RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private static RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private static SearchAndReserveSessionBeanRemote searchAndReserveSessionBeanRemote;
    @EJB
    private static AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote;

    
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(
            userSessionBeanRemote,
            roomSessionBeanRemote,
            roomTypeSessionBeanRemote,
            roomRateSessionBeanRemote,
            searchAndReserveSessionBeanRemote,
            allocateAndExceptionReportSessionBeanRemote);
        mainApp.runApp();
    }

}
