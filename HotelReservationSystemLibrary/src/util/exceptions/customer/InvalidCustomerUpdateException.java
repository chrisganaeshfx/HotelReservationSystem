package util.exceptions.customer;

public class InvalidCustomerUpdateException extends Exception {
    
    public InvalidCustomerUpdateException() {
    }

    public InvalidCustomerUpdateException(String message) {
        super(message);
    }
}