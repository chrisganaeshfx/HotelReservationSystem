package util.exceptions.roomtype;

public class InvalidRoomTypeUpdateException extends Exception
{
    public InvalidRoomTypeUpdateException() {
    }
    
    public InvalidRoomTypeUpdateException(String msg) {
        super(msg);
    }
}