package util.exceptions.search;

public class PastCheckInDateException extends Exception {
    
    public PastCheckInDateException() {
    }
    
    public PastCheckInDateException(String msg) {
        super(msg);
    }
    
}