/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */

package ejb.session.stateless;

import entity.ExceptionReport;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import util.exceptions.exceptionreport.ExceptionReportExistsException;
import util.exceptions.exceptionreport.ExceptionReportNotFoundException;
import util.exceptions.exceptionreport.InvalidExceptionReportException;
import util.exceptions.general.UnknownPersistenceException;

@Remote
public interface AllocateAndExceptionReportSessionBeanRemote {

    public Long createNewExceptionReport(ExceptionReport newExceptionReport) throws ExceptionReportExistsException, UnknownPersistenceException, InvalidExceptionReportException;

    public List<ExceptionReport> retrieveAllExceptionReports();

    public List<ExceptionReport> retrieveAllExceptionReportsForCheckInDate(Date checkInDate);

    public ExceptionReport retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException;
}
