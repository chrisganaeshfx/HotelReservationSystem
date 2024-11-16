package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import util.enums.RoomRateTypeEnum;


@Entity
public class RoomRate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private String roomType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomRateTypeEnum rateType;
    @Column(nullable = false)
    private boolean isPromotionOrPeakRate;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double ratePerNight;
    private Date startDate;
    private Date endDate;
    @Column(nullable = false)
    private boolean isEnabled;
    
    public RoomRate() {
    }

    public RoomRate(String roomType, RoomRateTypeEnum rateType, boolean isPromotionOrPeakRate, String name, double ratePerNight, Date startDate, Date endDate, boolean isEnabled) {
        this.roomType = roomType;
        this.rateType = rateType;
        this.isPromotionOrPeakRate = isPromotionOrPeakRate;
        this.name = name;
        this.ratePerNight = ratePerNight;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public RoomRateTypeEnum getRateType() {
        return rateType;
    }

    public void setRateType(RoomRateTypeEnum rateType) {
        this.rateType = rateType;
    }

    public boolean getIsPromotionOrPeakRate() {
        return isPromotionOrPeakRate;
    }

    public void setIsPromotionOrPeakRate(boolean isPromotionOrPeakRate) {
        this.isPromotionOrPeakRate = isPromotionOrPeakRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(double ratePerNight) {
        this.ratePerNight = ratePerNight;
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
