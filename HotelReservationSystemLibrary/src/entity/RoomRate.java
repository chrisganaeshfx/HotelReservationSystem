package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import util.enums.RateTypeEnum;


@Entity
public class RoomRate implements Serializable {

    //Own attributes
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "RateTypeEnum cannot be null")
    @Column(nullable = false)
    private RateTypeEnum rateType;
    @NotNull(message = "RatePerNight cannot be null")
    @Column(nullable = false)
    private double ratePerNight;
    @NotNull(message = "IsPromotionOrPeakRate cannot be null")
    @Column(nullable = false)
    private boolean isPromotionOrPeakRate;
    @Future(message = "StartDate must be in the future")
    private Date startDate;
    private Date endDate;
    @NotNull(message = "IsEnabled cannot be null")
    @Column(nullable = false)
    private boolean isEnabled;

    //Entity relationship atttributes
    @NotNull(message = "RoomType cannot be null")
    @ManyToOne(optional = false)
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;
    
    
    public RoomRate() {
    }

    public RoomRate(String name, RoomType roomType, RateTypeEnum rateType, double ratePerNight, boolean isPromotionOrPeakRate, Date startDate, Date endDate, boolean isEnabled) {
        this.name = name;
        this.roomType = roomType;
        this.rateType = rateType;
        this.ratePerNight = ratePerNight;
        this.isPromotionOrPeakRate = isPromotionOrPeakRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isEnabled = isEnabled;
    }

    public Long getRoomRateId() {
        return roomRateId;
    }

    public void setRoomRateId(Long roomRateId) {
        this.roomRateId = roomRateId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RateTypeEnum getRateType() {
        return rateType;
    }

    public void setRateType(RateTypeEnum rateType) {
        this.rateType = rateType;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(double ratePerNight) {
        this.ratePerNight = ratePerNight;
    }
    
    public boolean getIsPromotionOrPeakRate() {
        return isPromotionOrPeakRate;
    }

    public void setIsPromotionOrPeakRate(boolean isPromotionOrPeakRate) {
        this.isPromotionOrPeakRate = isPromotionOrPeakRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomRateId() != null ? getRoomRateId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the RoomRate fields are not set
        if (!(object instanceof RoomRate)) {
            return false;
        }
        RoomRate other = (RoomRate) object;
        if ((this.getRoomRateId() == null && other.getRoomRateId() != null) || (this.getRoomRateId() != null && !this.roomRateId.equals(other.roomRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomRate[ id=" + getRoomRateId() + " ]";
    }

}
