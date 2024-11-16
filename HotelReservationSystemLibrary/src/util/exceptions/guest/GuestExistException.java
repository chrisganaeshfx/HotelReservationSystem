package util.exceptions.guest;

public class GuestExistException extends Exception {
    public GuestExistException() {
    }

    public GuestExistException(String message) {
        super(message);
    }
}