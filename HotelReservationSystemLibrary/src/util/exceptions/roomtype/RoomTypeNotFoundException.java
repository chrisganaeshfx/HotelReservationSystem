package util.exceptions.roomtype;

public class RoomTypeNotFoundException extends Exception
{
    public RoomTypeNotFoundException() {
    }
    
    public RoomTypeNotFoundException(String msg) {
        super(msg);
    }
}