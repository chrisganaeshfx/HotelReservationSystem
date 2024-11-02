package util.exceptions.exceptionreport;

public class InvalidExceptionReportException extends Exception
{
    public InvalidExceptionReportException() {
    }
    
    public InvalidExceptionReportException(String msg) {
        super(msg);
    }
}