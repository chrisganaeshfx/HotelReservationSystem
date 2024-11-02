package util.exceptions.room;

public class RoomExistException extends Exception {
    public RoomExistException() {
    }

    public RoomExistException(String message) {
        super(message);
    }
}