package ejb.session.stateless;

import entity.ExceptionReport;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.exceptionreport.DeleteExceptionReportException;
import util.exceptions.exceptionreport.ExceptionReportExistsException;
import util.exceptions.exceptionreport.ExceptionReportNotFoundException;
import util.exceptions.exceptionreport.InvalidExceptionReportException;
import util.exceptions.general.UnknownPersistenceException;

@Remote
public interface ExceptionReportSessionBeanRemote {

    public Long createNewExceptionReport(ExceptionReport newExceptionReport) throws ExceptionReportExistsException, UnknownPersistenceException, InvalidExceptionReportException;

    public ExceptionReport retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException;

    public void updateExceptionReport(ExceptionReport updatedExceptionReport) throws ExceptionReportNotFoundException, InvalidExceptionReportException;

    public void deleteExceptionReport(Long exceptionReportId) throws ExceptionReportNotFoundException, DeleteExceptionReportException;

    public List<ExceptionReport> retrieveAllExceptionReport();
}
