package util.exceptions.general;

public class UnknownPersistenceException extends Exception
{
    public UnknownPersistenceException() {
    }
    
    public UnknownPersistenceException(String msg) {
        super(msg);
    }
}