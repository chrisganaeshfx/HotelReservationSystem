package util.exceptions.employee;

public class DeleteEmployeeException extends Exception {
    public DeleteEmployeeException() {
    }

    public DeleteEmployeeException(String message) {
        super(message);
    }
}