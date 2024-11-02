package util.exceptions.roomrate;

public class RoomRateExistException extends Exception
{
    public RoomRateExistException() {
    }

    public RoomRateExistException(String msg) {
        super(msg);
    }
}