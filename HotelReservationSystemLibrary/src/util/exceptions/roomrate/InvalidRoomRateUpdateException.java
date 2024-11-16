package util.exceptions.roomrate; 

public class InvalidRoomRateUpdateException extends Exception
{
    public InvalidRoomRateUpdateException() {
    }

    public InvalidRoomRateUpdateException(String msg) {
        super(msg);
    }
}