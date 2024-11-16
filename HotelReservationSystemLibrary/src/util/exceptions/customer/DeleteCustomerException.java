package util.exceptions.customer;

public class DeleteCustomerException extends Exception {
    public DeleteCustomerException() {
    }

    public DeleteCustomerException(String message) {
        super(message);
    }
}