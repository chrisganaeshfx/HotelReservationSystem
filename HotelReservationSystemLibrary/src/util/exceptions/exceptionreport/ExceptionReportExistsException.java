package util.exceptions.exceptionreport;

public class ExceptionReportExistsException extends Exception
{
    public ExceptionReportExistsException() {
    }
    
    public ExceptionReportExistsException(String msg) {
        super(msg);
    }
}