package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enums.ReservationStatusEnum;


@Entity
public class Reservation implements Serializable {

    //Own attributes
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @NotNull(message = "NumRooms cannot be null")
    @Min(value = 1, message = "NumRooms must at least be 1")
    @Max(value = 10, message = "NumRooms cannot exceed 10")
    @Column(nullable = false)
    private int numRooms;
    @Column(nullable = false)
    @Future(message = "CheckInDate must be in the future")
    private Date checkInDate;
    @NotNull(message = "CheckOutDate cannot be null")
    @Column(nullable = false)
    private Date checkOutDate;
    @NotNull(message = "ReservationStatusEnum cannot be null")
    @Column(nullable = false)
    private ReservationStatusEnum status;
    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private double amount;
    
    //Entity relationship attributes
    @NotNull(message = "Customer cannot be null")
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    private Guest customer;
    
    @NotNull(message = "RoomType cannot be null")
    @ManyToOne(optional = false)
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;
    
    @OneToMany(mappedBy = "allocatedReservation")
    private List<Room> allocatedRooms;

    public Reservation() {
    }

    public Reservation(Guest customer, RoomType roomType, int numRooms, Date checkInDate, Date checkOutDate, double amount) {
        this.customer = customer;
        this.roomType = roomType;
        this.numRooms = numRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatusEnum.RESERVED;
        this.amount = amount;
        this.allocatedRooms = new ArrayList<Room>();
    }

    
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    
    public Guest getCustomer() {
        return customer;
    }

    public void setCustomer(Guest customer) {
        this.customer = customer;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public ReservationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEnum status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public List<Room> getAllocatedRooms() {
        return allocatedRooms;
    }

    public void setAllocatedRooms(List<Room> allocatedRooms) {
        this.allocatedRooms = allocatedRooms;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reservationId != null ? reservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the reservationId fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.reservationId == null && other.reservationId != null) || (this.reservationId != null && !this.reservationId.equals(other.reservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Reservation[ id=" + reservationId + " ]";
    }


}
