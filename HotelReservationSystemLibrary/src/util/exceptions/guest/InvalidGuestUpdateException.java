package util.exceptions.guest;

public class InvalidGuestUpdateException extends Exception {
    public InvalidGuestUpdateException() {
    }

    public InvalidGuestUpdateException(String message) {
        super(message);
    }
}