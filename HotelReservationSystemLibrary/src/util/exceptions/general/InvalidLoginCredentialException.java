package util.exceptions.general;
        
public class InvalidLoginCredentialException extends Exception {
    public InvalidLoginCredentialException() {
    }

    public InvalidLoginCredentialException(String message) {
        super(message);
    }
}