package ejb.session.singleton;

import ejb.session.stateless.RoomRateSessionBeanLocal;
import ejb.session.stateless.RoomSessionBeanLocal;
import ejb.session.stateless.RoomTypeSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Employee;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enums.EmployeeRoleEnum;
import util.enums.RoomRateTypeEnum;
import util.exceptions.employee.EmployeeExistException;
import util.exceptions.employee.EmployeeNotFoundException;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.RoomExistException;
import util.exceptions.roomrate.RoomRateExistException;
import util.exceptions.roomtype.RoomTypeExistException;

@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB(name = "RoomSessionBeanLocal")
    private RoomSessionBeanLocal roomSessionBeanLocal;

    @EJB(name = "RoomRateSessionBeanLocal")
    private RoomRateSessionBeanLocal roomRateSessionBeanLocal;

    @EJB(name = "RoomTypeSessionBeanLocal")
    private RoomTypeSessionBeanLocal roomTypeSessionBeanLocal;

    @EJB(name = "UserSessionBeanLocal")
    private UserSessionBeanLocal userSessionBeanLocal;
    
    public DataInitSessionBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            userSessionBeanLocal.retrieveEmployeeById(1l);
        }
        catch(EmployeeNotFoundException ex)
        {
            initialiseData();
        }
    }
    
    public void initialiseData() {
        try
        {
            //Employee Initialisation
            userSessionBeanLocal.createNewEmployee(new Employee("sysadmin", "password", EmployeeRoleEnum.SYSTEM_ADMINISTRATOR));
            userSessionBeanLocal.createNewEmployee(new Employee("opmanager", "password", EmployeeRoleEnum.OPERATION_MANAGER));
            userSessionBeanLocal.createNewEmployee(new Employee("salesmanager", "password", EmployeeRoleEnum.SALES_MANAGER));
            userSessionBeanLocal.createNewEmployee(new Employee("guestrelo", "password", EmployeeRoleEnum.GUEST_RELATION_OFFICER));
            
            //RoomType and RoomRate Initialisation
            
            RoomType deluxeRoomType = new RoomType("Deluxe Room", "Premier Room", "description", "size", "bed", "capacity", "amenities");
RoomType premierRoomType = new RoomType("Premier Room", "Family Room", "description", "size", "bed", "capacity", "amenities");
RoomType familyRoomType = new RoomType("Family Room", "Junior Suite", "description", "size", "bed", "capacity", "amenities");
RoomType juniorSuiteRoomType = new RoomType("Junior Suite", "Grand Suite", "description", "size", "bed", "capacity", "amenities");
RoomType grandSuiteRoomType = new RoomType("Grand Suite", null, "description", "size", "bed", "capacity", "amenities");


            // Room Rates Initialization for Deluxe Room
            RoomRate deluxeRoomPublishedRate = new RoomRate(deluxeRoomType, RoomRateTypeEnum.PUBLISHED_RATE, false, "Deluxe Room Published", 100, null, null);
            RoomRate deluxeRoomNormalRate = new RoomRate(deluxeRoomType, RoomRateTypeEnum.NORMAL_RATE, false, "Deluxe Room Normal", 50, null, null);

            // Room Rates Initialization for Premier Room
            RoomRate premierRoomPublishedRate = new RoomRate(premierRoomType, RoomRateTypeEnum.PUBLISHED_RATE, false, "Premier Room Published", 200, null, null);
            RoomRate premierRoomNormalRate = new RoomRate(premierRoomType, RoomRateTypeEnum.NORMAL_RATE, false, "Premier Room Normal", 100, null, null);

            // Room Rates Initialization for Family Room
            RoomRate familyRoomPublishedRate = new RoomRate(familyRoomType, RoomRateTypeEnum.PUBLISHED_RATE, false, "Family Room Published", 300, null, null);
            RoomRate familyRoomNormalRate = new RoomRate(familyRoomType, RoomRateTypeEnum.NORMAL_RATE, false, "Family Room Normal", 150, null, null);

            // Room Rates Initialization for Junior Suite
            RoomRate juniorSuitePublishedRate = new RoomRate(juniorSuiteRoomType, RoomRateTypeEnum.PUBLISHED_RATE, false, "Junior Suite Published", 400, null, null);
            RoomRate juniorSuiteNormalRate = new RoomRate(juniorSuiteRoomType, RoomRateTypeEnum.NORMAL_RATE, false, "Junior Suite Normal", 200, null, null);

            // Room Rates Initialization for Grand Suite
            RoomRate grandSuitePublishedRate = new RoomRate(grandSuiteRoomType, RoomRateTypeEnum.PUBLISHED_RATE, false, "Grand Suite Published", 500, null, null);
            RoomRate grandSuiteNormalRate = new RoomRate(grandSuiteRoomType, RoomRateTypeEnum.NORMAL_RATE, false, "Grand Suite Normal", 250, null, null);

            // Persisting Room Types and Room Rates
            roomTypeSessionBeanLocal.createNewRoomType(deluxeRoomType);
            roomTypeSessionBeanLocal.createNewRoomType(premierRoomType);
            roomTypeSessionBeanLocal.createNewRoomType(familyRoomType);
            roomTypeSessionBeanLocal.createNewRoomType(juniorSuiteRoomType);
            roomTypeSessionBeanLocal.createNewRoomType(grandSuiteRoomType);

            // Persisting Room Rates
            roomRateSessionBeanLocal.createNewRoomRate(deluxeRoomPublishedRate);
            roomRateSessionBeanLocal.createNewRoomRate(deluxeRoomNormalRate);
            roomRateSessionBeanLocal.createNewRoomRate(premierRoomPublishedRate);
            roomRateSessionBeanLocal.createNewRoomRate(premierRoomNormalRate);
            roomRateSessionBeanLocal.createNewRoomRate(familyRoomPublishedRate);
            roomRateSessionBeanLocal.createNewRoomRate(familyRoomNormalRate);
            roomRateSessionBeanLocal.createNewRoomRate(juniorSuitePublishedRate);
            roomRateSessionBeanLocal.createNewRoomRate(juniorSuiteNormalRate);
            roomRateSessionBeanLocal.createNewRoomRate(grandSuitePublishedRate);
            roomRateSessionBeanLocal.createNewRoomRate(grandSuiteNormalRate);

            // Adding Room Rates to Room Types
            deluxeRoomType.getRoomRates().add(deluxeRoomPublishedRate);
            deluxeRoomType.getRoomRates().add(deluxeRoomNormalRate);
            premierRoomType.getRoomRates().add(premierRoomPublishedRate);
            premierRoomType.getRoomRates().add(premierRoomNormalRate);
            familyRoomType.getRoomRates().add(familyRoomPublishedRate);
            familyRoomType.getRoomRates().add(familyRoomNormalRate);
            juniorSuiteRoomType.getRoomRates().add(juniorSuitePublishedRate);
            juniorSuiteRoomType.getRoomRates().add(juniorSuiteNormalRate);
            grandSuiteRoomType.getRoomRates().add(grandSuitePublishedRate);
            grandSuiteRoomType.getRoomRates().add(grandSuiteNormalRate);
            

            // Room Initialization for Deluxe Room
            Room deluxeRoom1 = new Room(1, 1, true, true);
            Room deluxeRoom2 = new Room(2, 1, true, true);
            Room deluxeRoom3 = new Room(3, 1, true, true);
            Room deluxeRoom4 = new Room(4, 1, true, true);
            Room deluxeRoom5 = new Room(5, 1, true, true);

            // Assigning Deluxe RoomType to Deluxe Rooms
            deluxeRoom1.setRoomType(deluxeRoomType);
            deluxeRoom2.setRoomType(deluxeRoomType);
            deluxeRoom3.setRoomType(deluxeRoomType);
            deluxeRoom4.setRoomType(deluxeRoomType);
            deluxeRoom5.setRoomType(deluxeRoomType);

            // Room Initialization for Premier Room
            Room premierRoom1 = new Room(1, 2, true, true);
            Room premierRoom2 = new Room(2, 2, true, true);
            Room premierRoom3 = new Room(3, 2, true, true);
            Room premierRoom4 = new Room(4, 2, true, true);
            Room premierRoom5 = new Room(5, 2, true, true);

            // Assigning Premier RoomType to Premier Rooms
            premierRoom1.setRoomType(premierRoomType);
            premierRoom2.setRoomType(premierRoomType);
            premierRoom3.setRoomType(premierRoomType);
            premierRoom4.setRoomType(premierRoomType);
            premierRoom5.setRoomType(premierRoomType);

            // Room Initialization for Family Room
            Room familyRoom1 = new Room(1, 3, true, true);
            Room familyRoom2 = new Room(2, 3, true, true);
            Room familyRoom3 = new Room(3, 3, true, true);
            Room familyRoom4 = new Room(4, 3, true, true);
            Room familyRoom5 = new Room(5, 3, true, true);

            // Assigning Family RoomType to Family Rooms
            familyRoom1.setRoomType(familyRoomType);
            familyRoom2.setRoomType(familyRoomType);
            familyRoom3.setRoomType(familyRoomType);
            familyRoom4.setRoomType(familyRoomType);
            familyRoom5.setRoomType(familyRoomType);

            // Room Initialization for Junior Suite
            Room juniorSuiteRoom1 = new Room(1, 4, true, true);
            Room juniorSuiteRoom2 = new Room(2, 4, true, true);
            Room juniorSuiteRoom3 = new Room(3, 4, true, true);
            Room juniorSuiteRoom4 = new Room(4, 4, true, true);
            Room juniorSuiteRoom5 = new Room(5, 4, true, true);

            // Assigning Junior Suite RoomType to Junior Suite Rooms
            juniorSuiteRoom1.setRoomType(juniorSuiteRoomType);
            juniorSuiteRoom2.setRoomType(juniorSuiteRoomType);
            juniorSuiteRoom3.setRoomType(juniorSuiteRoomType);
            juniorSuiteRoom4.setRoomType(juniorSuiteRoomType);
            juniorSuiteRoom5.setRoomType(juniorSuiteRoomType);

            // Room Initialization for Grand Suite
            Room grandSuiteRoom1 = new Room(1, 5, true, true);
            Room grandSuiteRoom2 = new Room(2, 5, true, true);
            Room grandSuiteRoom3 = new Room(3, 5, true, true);
            Room grandSuiteRoom4 = new Room(4, 5, true, true);
            Room grandSuiteRoom5 = new Room(5, 5, true, true);

            // Assigning Grand Suite RoomType to Grand Suite Rooms
            grandSuiteRoom1.setRoomType(grandSuiteRoomType);
            grandSuiteRoom2.setRoomType(grandSuiteRoomType);
            grandSuiteRoom3.setRoomType(grandSuiteRoomType);
            grandSuiteRoom4.setRoomType(grandSuiteRoomType);
            grandSuiteRoom5.setRoomType(grandSuiteRoomType);

            // Persisting Rooms to the database
            roomSessionBeanLocal.createNewRoom(deluxeRoom1);
            roomSessionBeanLocal.createNewRoom(deluxeRoom2);
            roomSessionBeanLocal.createNewRoom(deluxeRoom3);
            roomSessionBeanLocal.createNewRoom(deluxeRoom4);
            roomSessionBeanLocal.createNewRoom(deluxeRoom5);

            roomSessionBeanLocal.createNewRoom(premierRoom1);
            roomSessionBeanLocal.createNewRoom(premierRoom2);
            roomSessionBeanLocal.createNewRoom(premierRoom3);
            roomSessionBeanLocal.createNewRoom(premierRoom4);
            roomSessionBeanLocal.createNewRoom(premierRoom5);

            roomSessionBeanLocal.createNewRoom(familyRoom1);
            roomSessionBeanLocal.createNewRoom(familyRoom2);
            roomSessionBeanLocal.createNewRoom(familyRoom3);
            roomSessionBeanLocal.createNewRoom(familyRoom4);
            roomSessionBeanLocal.createNewRoom(familyRoom5);

            roomSessionBeanLocal.createNewRoom(juniorSuiteRoom1);
            roomSessionBeanLocal.createNewRoom(juniorSuiteRoom2);
            roomSessionBeanLocal.createNewRoom(juniorSuiteRoom3);
            roomSessionBeanLocal.createNewRoom(juniorSuiteRoom4);
            roomSessionBeanLocal.createNewRoom(juniorSuiteRoom5);

            roomSessionBeanLocal.createNewRoom(grandSuiteRoom1);
            roomSessionBeanLocal.createNewRoom(grandSuiteRoom2);
            roomSessionBeanLocal.createNewRoom(grandSuiteRoom3);
            roomSessionBeanLocal.createNewRoom(grandSuiteRoom4);
            roomSessionBeanLocal.createNewRoom(grandSuiteRoom5);

        }
        catch(EmployeeExistException | RoomTypeExistException | RoomRateExistException | RoomExistException | UnknownPersistenceException ex) //InputDataValidationException ex
        {
            System.out.println("Error occured: " + ex.getMessage());
        }
    
    }
}
