package util.exceptions.room;

public class InvalidRoomUpdateException extends Exception {
    public InvalidRoomUpdateException() {
    }

    public InvalidRoomUpdateException(String message) {
        super(message);
    }
}