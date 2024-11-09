/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */

package ejb.session.stateless;

import entity.ExceptionReport;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import util.exceptions.exceptionreport.DeleteExceptionReportException;
import util.exceptions.exceptionreport.ExceptionReportExistsException;
import util.exceptions.exceptionreport.ExceptionReportNotFoundException;
import util.exceptions.exceptionreport.InvalidExceptionReportException;
import util.exceptions.general.UnknownPersistenceException;

@Stateless
public class ExceptionReportSessionBean implements ExceptionReportSessionBeanRemote, ExceptionReportSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewExceptionReport(ExceptionReport newExceptionReport) throws ExceptionReportExistsException, UnknownPersistenceException, InvalidExceptionReportException {
        try {
            validateExceptionReport(newExceptionReport);
            em.persist(newExceptionReport);
            em.flush();
            return newExceptionReport.getExceptionReportId();
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
    public List<ExceptionReport> retrieveAllExceptionReport() {
        Query query = em.createQuery("SELECT e FROM ExceptionReport e");
        return query.getResultList();
    }

    @Override
    public ExceptionReport retrieveExceptionReportById(Long exceptionReportId) throws ExceptionReportNotFoundException {
        ExceptionReport exceptionReport = em.find(ExceptionReport.class, exceptionReportId);
        if (exceptionReport == null) {
            throw new ExceptionReportNotFoundException("Exception report ID " + exceptionReportId + " does not exist!");
        }
        return exceptionReport;
    }

    @Override
    public void updateExceptionReport(ExceptionReport updatedExceptionReport) throws ExceptionReportNotFoundException, InvalidExceptionReportException {
        if (updatedExceptionReport != null && updatedExceptionReport.getExceptionReportId() != null) {
            ExceptionReport exceptionReportToUpdate = retrieveExceptionReportById(updatedExceptionReport.getExceptionReportId());
            if (exceptionReportToUpdate == null) {
                throw new ExceptionReportNotFoundException("Exception report to update not found with ID: " + updatedExceptionReport.getExceptionReportId());
            }

            validateExceptionReport(updatedExceptionReport);

            exceptionReportToUpdate.setType(updatedExceptionReport.getType());
            //exceptionReportToUpdate.setReservation(updatedExceptionReport.getReservation());
            exceptionReportToUpdate.setDescription(updatedExceptionReport.getDescription());
        } else {
            throw new InvalidExceptionReportException("Exception report information to be updated is invalid or incomplete!");
        }
    }

    @Override
    public void deleteExceptionReport(Long exceptionReportId) throws ExceptionReportNotFoundException, DeleteExceptionReportException {
        ExceptionReport exceptionReportToRemove = retrieveExceptionReportById(exceptionReportId);
        try {
            em.remove(exceptionReportToRemove);
        } catch (PersistenceException ex) {
            throw new DeleteExceptionReportException("Unable to delete exception report with ID " + exceptionReportId);
        }
    }

    private void validateExceptionReport(ExceptionReport exceptionReport) throws InvalidExceptionReportException {
        if (exceptionReport.getType() == null) {
            throw new InvalidExceptionReportException("ExceptionReportTypeEnum cannot be null.");
        }
        //if (exceptionReport.getReservation() == null) {
          //  throw new InvalidExceptionReportException("Reservation cannot be null.");
        //}
    }
}

