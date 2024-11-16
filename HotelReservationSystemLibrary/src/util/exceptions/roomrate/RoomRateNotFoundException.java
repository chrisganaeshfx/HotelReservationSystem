package util.exceptions.roomrate;

public class RoomRateNotFoundException extends Exception
{
    public RoomRateNotFoundException() {
    }

    public RoomRateNotFoundException(String msg) {
        super(msg);
    }
}