/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author minseokim
 */
@Entity
public class RoomTypeAvailability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeAvailabilityId;
    @Column(nullable = false)
    private Date reservationDate;
    @Column(nullable = false)
    private int availability;

    public RoomTypeAvailability() {
    }

    public RoomTypeAvailability(Date reservationDate, int availability) {
        this.reservationDate = reservationDate;
        this.availability = availability;
    }
    
    

    public Long getRoomTypeAvailabilityId() {
        return roomTypeAvailabilityId;
    }

    public void setRoomTypeAvailabilityId(Long roomTypeAvailabilityId) {
        this.roomTypeAvailabilityId = roomTypeAvailabilityId;
    }
    
    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeAvailabilityId != null ? roomTypeAvailabilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomTypeAvailabilityId fields are not set
        if (!(object instanceof RoomTypeAvailability)) {
            return false;
        }
        RoomTypeAvailability other = (RoomTypeAvailability) object;
        if ((this.roomTypeAvailabilityId == null && other.roomTypeAvailabilityId != null) || (this.roomTypeAvailabilityId != null && !this.roomTypeAvailabilityId.equals(other.roomTypeAvailabilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomTypeAvailability[ id=" + roomTypeAvailabilityId + " ]";
    }
    
}
