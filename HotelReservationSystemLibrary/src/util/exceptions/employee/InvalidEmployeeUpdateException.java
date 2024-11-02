package util.exceptions.employee;

public class InvalidEmployeeUpdateException extends Exception {
    public InvalidEmployeeUpdateException() {
    }

    public InvalidEmployeeUpdateException(String message) {
        super(message);
    }
}