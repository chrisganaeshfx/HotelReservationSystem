/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.ExceptionReport;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exceptions.exceptionreport.ExceptionReportExistsException;
import util.exceptions.exceptionreport.ExceptionReportNotFoundException;
import util.exceptions.exceptionreport.InvalidExceptionReportException;
import util.exceptions.general.UnknownPersistenceException;

@Stateless
public class AllocateAndExceptionReportSessionBean implements AllocateAndExceptionReportSessionBeanRemote, AllocateAndExceptionReportSessionBeanLocal {
    
    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewExceptionReport(ExceptionReport newExceptionReport) throws ExceptionReportExistsException, UnknownPersistenceException, InvalidExceptionReportException {
        try {
            em.persist(newExceptionReport);
            em.flush();
            Long newExceptionReportId = newExceptionReport.getExceptionReportId();
            System.out.println("Successfully created ExceptionReport with exceptionReport Id " + newExceptionReportId + "!\n");
            return newExceptionReportId;
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ExceptionReportExistsException("Exception report already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<ExceptionReport> retrieveAllExceptionReports() {
        Query query = em.createQuery("SELECT e FROM ExceptionReport e ORDER BY e.type DESC");
        List<ExceptionReport> exceptionReports = query.getResultList();
        for(ExceptionReport e : exceptionReports) {
            e.getReservation();
        }
        return exceptionReports;
    }
    
    @Override
    public List<ExceptionReport> retrieveAllExceptionReportsForCheckInDate(Date checkInDate) {
        Query query = em.createQuery("SELECT e FROM ExceptionReport e JOIN e.reservation res WHERE res.checkInDate = :inCheckInDate ORDER BY e.exceptionReportId ASC, e.type DESC");
        List<ExceptionReport> exceptionReports = query.getResultList();
        for(ExceptionReport e : exceptionReports) {
            e.getReservation();
        }
        return exceptionReports;
    }

    @Override
    public ExceptionReport retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException {
        ExceptionReport exceptionReport = em.find(ExceptionReport.class, exceptionReportId);
        if (exceptionReport == null) {
            throw new ExceptionReportNotFoundException("Exception report ID " + exceptionReportId + " does not exist!");
        }
        return exceptionReport;
    }
    
    
    
    
}
