package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.ExceptionReportSessionBeanLocal;
import ejb.session.stateless.RoomRateSessionBeanLocal;
import ejb.session.stateless.RoomSessionBeanLocal;
import ejb.session.stateless.RoomTypeSessionBeanLocal;
import entity.Employee;
import entity.ExceptionReport;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enums.EmployeeRoleEnum;
import util.enums.ExceptionReportTypeEnum;
import util.enums.RoomRateTypeEnum;
import util.enums.RoomTypeEnum;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.employee.EmployeeNotFoundException;
import util.exceptions.exceptionreport.ExceptionReportExistsException;
import util.exceptions.exceptionreport.InvalidExceptionReportException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.RoomExistException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomtype.RoomTypeExistException;

@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB(name = "ExceptionReportSessionBeanLocal")
    private ExceptionReportSessionBeanLocal exceptionReportSessionBeanLocal;

    @EJB(name = "RoomSessionBeanLocal")
    private RoomSessionBeanLocal roomSessionBeanLocal;

    @EJB(name = "RoomRateSessionBeanLocal")
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;

    @EJB(name = "RoomTypeSessionBeanLocal")
    private RoomTypeSessionBeanLocal roomTypeSessionBeanLocal;

    @EJB(name = "EmployeeSessionBeanLocal")
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;
    
    public DataInitSessionBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            employeeSessionBeanLocal.retrieveEmployeeByUsername("default_admin");
        }
        catch(EmployeeNotFoundException ex)
        {
            initialiseData();
        }
    }
    
    public void initialiseData() {
        try
        {
            employeeSessionBeanLocal.createNewEmployee(new Employee("default_admin", "123", EmployeeRoleEnum.SYSTEM_ADMINISTRATOR));

            roomTypeSessionBeanLocal.createNewRoomType(new RoomType(RoomTypeEnum.DELUXE_ROOM, "deluxe room description", 100, "deluxe room bed", 2, "deluxe room amenities", true));
            roomTypeSessionBeanLocal.createNewRoomType(new RoomType(RoomTypeEnum.PREMIER_ROOM, "premier room description", 120, "premier room bed", 2, "premier room amenities", true));
            roomTypeSessionBeanLocal.createNewRoomType(new RoomType(RoomTypeEnum.FAMILY_ROOM, "family room description", 150, "family room bed", 4, "family room amenities", true));
            //roomTypeSessionBeanLocal.createNewRoomType(new RoomType(RoomTypeEnum.JUNIOR_SUITE, "junior suite description", 180, "junior suite bed", 6, "junior suite amenities", true));
            //roomTypeSessionBeanLocal.createNewRoomType(new RoomType(RoomTypeEnum.GRAND_SUITE, "grand suite description", 200, "grand suite bed", 6, "grand suite amenities", true));
            
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Deluxe Room Published Rate", RoomRateTypeEnum.PUBLISHED_RATE, 100.00, false, null, null, true));
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Deluxe Room Normal Rate", RoomRateTypeEnum.NORMAL_RATE, 90.00, false, null, null, true));
            
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Premier Room Published Rate", RoomRateTypeEnum.PUBLISHED_RATE, 110.00, false, null, null, true));
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Premier Room Normal Rate", RoomRateTypeEnum.NORMAL_RATE, 100.00, false, null, null, true));
            
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Family Room Published Rate", RoomRateTypeEnum.PUBLISHED_RATE, 120.00, false, null, null, true));
            roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Family Room Normal Rate", RoomRateTypeEnum.NORMAL_RATE, 110.00, false, null, null, true));
            
            //roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Junior Suite Published Rate", RoomRateTypeEnum.PUBLISHED_RATE, 130.00, false, null, null, true));
            //roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Junior Suite Normal Rate", RoomRateTypeEnum.NORMAL_RATE, 120.00, false, null, null, true));
            
            //roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Grand Suite Published Rate", RoomRateTypeEnum.PUBLISHED_RATE, 140.00, false, null, null, true));
            //roomRateSessionBeanLocal.createNewRoomRate(new RoomRate("Grand Suite Normal Rate", RoomRateTypeEnum.NORMAL_RATE, 130.00, false, null, null, true));
            
            exceptionReportSessionBeanLocal.createNewExceptionReport(new ExceptionReport(ExceptionReportTypeEnum.TYPE_1, "Room upgraded"));
            exceptionReportSessionBeanLocal.createNewExceptionReport(new ExceptionReport(ExceptionReportTypeEnum.TYPE_2, "No room available"));
            
            for (int i = 200; i <= 2325; i += 100) {
                for (int j = 1; j <= 25; j++) {
                    int no = i+j;
                    roomSessionBeanLocal.createNewRoom(new Room(no, true, true));
                } 
            }
        }
        catch(EmployeeExistException | RoomTypeExistException | UnknownPersistenceException | RoomRateExistException | ExceptionReportExistsException | RoomExistException | InvalidExceptionReportException ex) //InputDataValidationException ex
        {
            ex.printStackTrace();
        }
    
    }
}
