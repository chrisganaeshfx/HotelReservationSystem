package util.exceptions.roomtype;

public class RoomTypeExistException extends Exception
{
    public RoomTypeExistException() {
    }
    
    public RoomTypeExistException(String msg) {
        super(msg);
    }
}