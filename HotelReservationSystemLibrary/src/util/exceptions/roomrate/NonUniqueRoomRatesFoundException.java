package util.exceptions.roomrate; 

public class NonUniqueRoomRatesFoundException extends Exception
{
    public NonUniqueRoomRatesFoundException() {
    }

    public NonUniqueRoomRatesFoundException(String msg) {
        super(msg);
    }
}