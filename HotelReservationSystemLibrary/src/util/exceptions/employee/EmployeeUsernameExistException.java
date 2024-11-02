package util.exceptions.employee;

public class EmployeeUsernameExistException extends Exception {
    public EmployeeUsernameExistException() {
    }

    public EmployeeUsernameExistException(String message) {
        super(message);
    }
}