package util.exceptions.customer;


public class CustomerExistException extends Exception {
    public CustomerExistException() {
    }

    public CustomerExistException(String message) {
        super(message);
    }
}