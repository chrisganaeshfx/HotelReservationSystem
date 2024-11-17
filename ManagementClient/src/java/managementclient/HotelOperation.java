/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package managementclient;

import ejb.session.stateless.AllocateAndExceptionReportSessionBeanRemote;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.Employee;
import entity.ExceptionReport;
import entity.Room;
import entity.RoomRate;
import entity.RoomType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.exceptions.general.UnknownPersistenceException;
import util.exceptions.room.DeleteRoomException;
import util.exceptions.room.InvalidRoomUpdateException;
import util.exceptions.room.RoomNotFoundException;
import util.exceptions.roomtype.DeleteRoomTypeException;
import util.exceptions.roomtype.InvalidRoomTypeUpdateException;
import util.exceptions.roomtype.RoomTypeExistException;
import util.exceptions.roomtype.RoomTypeNotFoundException;
import util.helper.ClientHelper;


public class HotelOperation {

    @EJB
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    @EJB
    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    @EJB
    private RoomSessionBeanRemote roomSessionBeanRemote;
    @EJB
    private AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote;
    private Scanner scanner = new Scanner(System.in);
    private int response = 0;

    public HotelOperation() {
    }

    public HotelOperation(
            RoomTypeSessionBeanRemote roomTypeSessionBeanRemote,
            RoomRateSessionBeanRemote roomRateSessionBeanRemote,
            RoomSessionBeanRemote roomSessionBeanRemote,
            AllocateAndExceptionReportSessionBeanRemote allocateAndExceptionReportSessionBeanRemote) {

        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.allocateAndExceptionReportSessionBeanRemote = allocateAndExceptionReportSessionBeanRemote;
    }
    
    public void showMenu(Employee currentEmployee) {
        
        while (true) {
            System.out.println("\n*** Hotel Operation Module ***");
            System.out.println("You are login as '" + currentEmployee.getUsername() + "' with " + currentEmployee.getRole().toString() + " rights!\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View All Room Types");
            /*
            To be placed under (2)
            System.out.println("1: Update Room Type");
            System.out.println("2: Delete Room Type");
            */
            System.out.println("3: View Room Type Details");
            System.out.println("4: Create New Room");
            System.out.println("5: View All Rooms");
            System.out.println("6: Update Room");
            System.out.println("7: Delete Room");
            System.out.println("8: View Room Allocation Exception Report");
            System.out.println("9: Back");
            response = 0;

            while (response < 1 || response > 9) {
                System.out.print("> ");
                int response = scanner.nextInt();
                
                if(response == 1) {
                    System.out.println("\n*** Hotel Operation :: Create New Room Type ***");
                    createNewRoomType();
                }
                else if(response == 2) {
                    System.out.println("\n*** Hotel Operation :: View All Room Type ***");
                    viewAllRoomTypes();
                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                }
                else if(response == 3) {
                    System.out.println("\n*** Hotel Operation :: View Room Type Details ***");
                    // viewAllRoomTypes();
                    viewRoomTypeDetails();  
                } 
                else if(response == 4) {
                    System.out.println("\n*** Hotel Operation :: Create New Room ***");
                    createNewRoom();
                } 
                else if(response == 5) {
                    System.out.println("\n*** Hotel Operation :: View All Rooms ***");
                    viewAllRooms();
                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();
                    scanner.nextLine();
                }
                else if(response == 6) {
                    System.out.println("\n*** Hotel Operation :: Update Room ***");
                    // viewAllRooms();
                    updateRoom();
                    break;
                }
                else if(response == 7) {
                    System.out.println("\n*** Hotel Operation :: Delete Room ***");
                    // viewAllRooms();
                    deleteOrDisableRoom();
                    break;
                }
                else if(response == 8) {
                    System.out.println("\n*** Hotel Operation :: View Room Allocation Exception Reports ***");
                    viewRoomAllocationExceptionReports(inputCheckInDate());
                }
                else if(response == 9) {
                    break;
                }
                else {
                    System.out.println("Invalid option. Try again.");
                } 
            }
            
            if (response == 9) {
                break;
            }
        }
    }
    
    // Room Type CRUD
    public RoomType inputRoomTypeAttributes(RoomType roomType) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Room Type Name> ");
        String name = scanner.nextLine();

        // Use retrieveAllRoomTypes to display options for nextHighestRoomType
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.retrieveAllEnabledRoomTypes();
        System.out.println("Select Next Highest Room Type (by number):");
        ClientHelper.displayNumberedList(roomTypes);
        System.out.print("Enter choice> ");
        int selection = Integer.parseInt(scanner.nextLine());
        RoomType nextHighestRoomType = roomTypes.get(selection - 1); // Fetch the selected RoomType
        String nextHighestRoomTypeName = nextHighestRoomType.getName();

        System.out.print("Enter Room Type Description> ");
        String description = scanner.nextLine();

        System.out.print("Enter Room Size> ");
        String size = scanner.nextLine();

        System.out.print("Enter Bed Type> ");
        String bed = scanner.nextLine();

        System.out.print("Enter Capacity> ");
        String capacity = scanner.nextLine();

        System.out.print("Enter Amenities (comma-separated)> ");
        String amenities = scanner.nextLine();
        
        System.out.println("Is this room type enabled?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.print("Enter choice> ");
        int enabledChoice = Integer.parseInt(scanner.nextLine());
        boolean isEnabled = (enabledChoice == 1);

        roomType.setName(name);
        roomType.setNextHighestRoomTypeName(nextHighestRoomTypeName);
        roomType.setDescription(description);
        roomType.setSize(size);
        roomType.setBed(bed);
        roomType.setCapacity(capacity);
        roomType.setAmenities(amenities);
        roomType.setIsEnabled(isEnabled);
        System.out.println("Room Type attributes successfully inputted.\n");

        return roomType;
    }
    
    public void createNewRoomType() {
        RoomType newRoomType = new RoomType();
        newRoomType = inputRoomTypeAttributes(newRoomType);
        try {
            Long newRoomTypeId = roomTypeSessionBeanRemote.createNewRoomType(newRoomType);
            System.out.println("Successfully created new room type with ID: " + newRoomTypeId);
        } catch (RoomTypeExistException | UnknownPersistenceException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void viewAllRoomTypes() {
        List<RoomType> allRoomTypes = roomTypeSessionBeanRemote.retrieveAllRoomTypes();
        System.out.println("-------------------------------");
        System.out.printf("%11s%16s\n", "RoomType ID", "RoomType Name");
        for (RoomType rt : allRoomTypes) {
            System.out.printf("%11s%16s\n", rt.getRoomTypeId().toString(), rt.getName().toString());
        }
    }

    public void viewRoomTypeDetails() {
        System.out.print("Enter RoomType Id for details> ");
        int rawInput = scanner.nextInt();
        Long convertedInput = Long.valueOf("" + rawInput);
        try {
            RoomType roomType = roomTypeSessionBeanRemote.retrieveRoomTypeById(convertedInput);
            System.out.println("\n" + roomType.getName() + " details");
            System.out.println("----------------------------------");
            System.out.println("RoomTypeId: " + roomType.getRoomTypeId());
            System.out.println("Name: " + roomType.getName());
            System.out.println("NextHighestRoomType Name: " + roomType.getName());
            System.out.println("Description: " + roomType.getDescription());
            System.out.println("Size: " + roomType.getSize());
            System.out.println("Bed: " + roomType.getBed());
            System.out.println("Capacity: " + roomType.getCapacity());
            System.out.println("Amenities: " + roomType.getAmenities());
            System.out.println("IsEnabled: " + roomType.getIsEnabled());
            System.out.print("\n");
            updateAndDeleteRoomTypeMenu(roomType);
        } catch (RoomTypeNotFoundException ex) {
            System.out.println("Error occured: " + ex.getMessage() + "\n");
        }
    }
    
    private void updateAndDeleteRoomTypeMenu(RoomType roomType) {
        System.out.println("1: Update Room Type");
        System.out.println("2: Delete Room Type");
        System.out.println("3: Back");
        response = 0;
                    
        while(response < 1 || response > 3) {
            System.out.print("> ");
            response = scanner.nextInt();
            
            if(response == 1) {
                System.out.println("\n*** Hotel Operation :: Update " + roomType.getName() + " ***");
                updateRoomType(roomType);
            }
            else if(response == 2) {
                System.out.println("\n*** Hotel Operation :: Delete " + roomType.getName() + " ***");
                deleteOrDisableRoomType(roomType);
            }
            else if(response == 3) {
                return;
            } 
            else {
                System.out.println("Invalid option, please try again!\n");
            }
        }
    }
    
    private void updateRoomType(RoomType roomTypeToUpdate) {
        RoomType updatedRoomType = inputRoomTypeAttributes(roomTypeToUpdate);
        try {
            roomTypeSessionBeanRemote.updateRoomType(updatedRoomType);
            System.out.println("Successfully updated RoomType with Id: " + updatedRoomType.getRoomTypeId() + "!\n");
        } catch (RoomTypeNotFoundException | InvalidRoomTypeUpdateException ex) {
            System.out.println("Error occured: " + ex.getMessage() + "\n");
        }
    }

    private void deleteOrDisableRoomType(RoomType roomTypeToDelete) {
        Long roomTypeToDeleteId = roomTypeToDelete.getRoomTypeId();
        boolean isRoomTypeUsed = roomTypeSessionBeanRemote.isRoomTypeUsed(roomTypeToDelete.getName());
        
        if(isRoomTypeUsed) {
            try {
                roomTypeSessionBeanRemote.disableRoomType(roomTypeToDeleteId);
                System.out.println("RoomType with Id: " + roomTypeToDeleteId + "disabled since it is being used!");
            } catch (RoomTypeNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
            }
        } else {
            try {
                roomTypeSessionBeanRemote.deleteRoomType(roomTypeToDeleteId);
                System.out.println("Successfully deleted RoomType with Id: " + roomTypeToDeleteId + "!\n");
            } catch (DeleteRoomTypeException | RoomTypeNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
            }
        }
    }

    public Room inputRoomAttributes(Room room) {
        
        // Use retrieveAllEnabledRoomTypes to display available Room Types
        List<RoomType> roomTypes = roomTypeSessionBeanRemote.retrieveAllEnabledRoomTypes();
        System.out.println("Select Room Type (by number):");
        ClientHelper.displayNumberedList(roomTypes);
        System.out.print("Enter choice> ");
        int selection = Integer.parseInt(scanner.nextLine());
        RoomType selectedRoomType = roomTypes.get(selection - 1); 
        room.setRoomType(selectedRoomType);
        
        int floor;
        while (true) {
            System.out.print("Enter Floor Number (1-99)> ");
            floor = Integer.parseInt(scanner.nextLine());
            if (floor >= 1 && floor <= 99) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 99.");
            }
        }
        room.setFloor(floor);
        
        int unit;
        while (true) {
            System.out.print("Enter Unit Number (1-99)> ");
            unit = Integer.parseInt(scanner.nextLine());
            if (unit >= 1 && unit <= 99) {
                break;
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 99.");
            }
        }
        room.setUnit(unit);
        room.setRoomNumber(String.format("%02d%02d", floor, unit));

        System.out.println("Is this room available?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.print("Enter choice> ");
        int availabilityChoice = Integer.parseInt(scanner.nextLine());
        boolean isAvailable = (availabilityChoice == 1);
        room.setIsAvailable(isAvailable);
        System.out.println("Room attributes successfully inputted.\n");

        room.setIsEnabled(true);
        return room;
    }
    
    public void createNewRoom() {
        Room newRoom = new Room();
        newRoom = inputRoomAttributes(newRoom);
        try {
            Long newRoomId = roomSessionBeanRemote.createNewRoom(newRoom);
            System.out.println("Successfully created new room with ID: " + newRoomId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewAllRooms() {
        List<Room> allRooms = roomSessionBeanRemote.retrieveAllRooms();
        System.out.printf("%7s%12s%14s\n", "Room_Id", "Room_Number", "Room_Type");
        for(Room r : allRooms) {
            System.out.printf("%7s%12s%14s\n", r.getRoomId(), r.getRoomNumber(), r.getRoomType());
        }
    }

    public void updateRoom() {
        Room roomToUpdate = new Room();
        
        while(roomToUpdate.getRoomId() == null) {
            try {
                System.out.print("Enter Room Id to update> ");
                int rawInput = scanner.nextInt();
                Long convertedInput = Long.valueOf("" + rawInput);
                roomToUpdate = roomSessionBeanRemote.retrieveRoomById(convertedInput);
                System.out.println("Room with Id: " + roomToUpdate.getRoomId().toString() + "found!");
                
            } catch (RoomNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
                System.out.println("Invalid Room Id, please try again!");
            }
        }
        
        Room updatedRoom = inputRoomAttributes(roomToUpdate);
        
        try {
            roomSessionBeanRemote.updateRoom(updatedRoom);
            System.out.println("Successfully updated Room with Id: " + updatedRoom.getRoomId() + "!\n");
        } catch (RoomNotFoundException | InvalidRoomUpdateException ex) {
            System.out.println("Error occured: " + ex.getMessage() + "\n");
        }
    }

    public void deleteOrDisableRoom() {
        Room roomToDelete = null;
        
        while(roomToDelete == null) {
            try {
                System.out.print("Enter Room Id to delete> ");
                int rawInput = scanner.nextInt();
                Long convertedInput = Long.valueOf("" + rawInput);
                roomToDelete = roomSessionBeanRemote.retrieveRoomById(convertedInput);
                System.out.println("Room with Id: " + roomToDelete.getRoomId().toString() + "found!");
                
            } catch (RoomNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
                System.out.println("Invalid Room Id, please try again!");
            }
        }
        
        Long roomToDeleteId = roomToDelete.getRoomId();
        boolean isRoomUsed = roomSessionBeanRemote.isRoomUsed(roomToDeleteId);
        
        if(isRoomUsed) {
            try {
                roomSessionBeanRemote.disableRoom(roomToDeleteId);
                System.out.println("Room with Id: " + roomToDeleteId + "disabled since it is being used!");
            } catch (RoomNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
            }
        } else {
            try {
                roomSessionBeanRemote.deleteRoom(roomToDeleteId);
                System.out.println("Successfully deleted Room with Id: " + roomToDeleteId + "!\n");
            } catch (DeleteRoomException | RoomNotFoundException ex) {
                System.out.println("Error occured: " + ex.getMessage() + "\n");
            }
        }
    }
    
    private Date inputCheckInDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        
        Date checkInDate = null;
        
        while (checkInDate == null) {    
            try {
                System.out.print("Enter check-in date in DD/MM/YYYY format> ");
                checkInDate = dateFormat.parse(scanner.nextLine()); 
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in DD/MM/YYYY format.");
            }
        }
        return checkInDate;
    }
    
    public void viewRoomAllocationExceptionReports(Date checkInDate) {
        List<ExceptionReport> exceptionReportsForCheckInDate = allocateAndExceptionReportSessionBeanRemote.retrieveAllExceptionReportsForCheckInDate(checkInDate);
        System.out.printf("%11s,%6s,%11s", "AE_ReportId", "Type", "Description");
        for(ExceptionReport er : exceptionReportsForCheckInDate) {
            System.out.printf("%11s,%6s,%11s", er.getExceptionReportId(), er.getType(), er.getDescription());
        }
    }
   

}
