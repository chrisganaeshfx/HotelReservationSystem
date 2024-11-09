package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import util.enums.ExceptionReportTypeEnum;


@Entity
public class ExceptionReport implements Serializable {

    //Own attributes
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exceptionReportId;
    @NotNull(message = "ExceptionReportTypeEnum cannot be null")
    @Column(nullable = false)
    private ExceptionReportTypeEnum type;
    private String description;
    
    //Entity relationship attributes
    //@NotNull(message = "Reservation cannot be null")
    //@ManyToOne(optional = false)
    //@JoinColumn(name = "reservationId", nullable = false)
    //private Reservation reservation;    

    public ExceptionReport() {
    }

    public ExceptionReport(ExceptionReportTypeEnum type, String description) {
        this.type = type;
        this.description = description;
    }

    //public ExceptionReport(ExceptionReportTypeEnum type, String description, Reservation reservation) {
      //  this.type = type;
    //    this.reservation = reservation;
      //  this.description = description;
    //}

    public Long getExceptionReportId() {
        return exceptionReportId;
    }

    public void setExceptionReportId(Long exceptionReportId) {
        this.exceptionReportId = exceptionReportId;
    }

    public ExceptionReportTypeEnum getType() {
        return type;
    }

    public void setType(ExceptionReportTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
    //public Reservation getReservation() {
      //  return reservation;
    //}

    //public void setReservation(Reservation reservation) {
    //    this.reservation = reservation;
    //}

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exceptionReportId != null ? exceptionReportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the exceptionReportId fields are not set
        if (!(object instanceof ExceptionReport)) {
            return false;
        }
        ExceptionReport other = (ExceptionReport) object;
        if ((this.exceptionReportId == null && other.exceptionReportId != null) || (this.exceptionReportId != null && !this.exceptionReportId.equals(other.exceptionReportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ExceptionReport[ id=" + exceptionReportId + " ]";
    }

}
