package util.exceptions.employee;

public class EmployeeExistException extends Exception {
    public EmployeeExistException() {
    }

    public EmployeeExistException(String message) {
        super(message);
    }
}