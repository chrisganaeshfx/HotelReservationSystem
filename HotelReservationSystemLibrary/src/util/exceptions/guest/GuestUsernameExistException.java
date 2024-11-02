package util.exceptions.guest;

public class GuestUsernameExistException extends Exception {
    public GuestUsernameExistException() {
    }

    public GuestUsernameExistException(String message) {
        super(message);
    }
}