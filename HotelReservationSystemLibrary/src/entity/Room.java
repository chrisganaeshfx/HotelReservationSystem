package entity;

import java.io.Serializable;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class Room implements Serializable {

    //Own attributes
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private RoomType roomType;
    
    @Min(value = 1)
    @Max(value = 99)
    @Column(nullable = false)
    private int floor;
    @Min(value = 1)
    @Max(value = 99)
    @Column(nullable = false)
    private int unit;
    @Column(nullable = false, unique = true)
    private String roomNumber = String.format("%02d%02d", getFloor(), getUnit());
    @Column(nullable = false)
    private boolean isAvailable;
    @Column(nullable = false)
    private boolean isEnabled;
    
    public Room() {
    }

    public Room(int floor, int unit, boolean isAvailable, boolean isEnabled) {
        this.floor = floor;
        this.unit = unit;
        this.roomNumber = String.format("%02d%02d", floor, unit);
        this.isAvailable = isAvailable;
        this.isEnabled = true;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean roomIsAvailable) {
        this.isAvailable = roomIsAvailable;
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
        hash += (getRoomId() != null ? getRoomId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomId fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.getRoomId() == null && other.getRoomId() != null) || (this.getRoomId() != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return roomNumber + "(Id: " + roomId + ")";
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
