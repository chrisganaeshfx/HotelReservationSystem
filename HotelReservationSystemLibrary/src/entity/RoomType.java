/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enums.RoomTypeEnum;


@Entity
public class RoomType implements Serializable {

    //Own attributes
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private RoomTypeEnum roomTypeName;
    private String description;
    @NotNull(message = "Size cannot be null")
    @Min(value = 1, message = "Size must be at least 1 sqm")
    @Max(value = 200, message = "Size cannot exceed 200 sqm")
    @Column(nullable = false)
    private int size;
    private String bed;
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1 pax")
    @Max(value = 20, message = "Capacity cannot exceed 20 pax")
    @Column(nullable = false)
    private int capacity;
    private String amenities;
    @Column(nullable = false)
    private Boolean isEnabled;
    //@NotNull(message = "RoomInventoryOverTime cannot be null")
    //@NotNull(message = "Room Type Availability cannot be null")
    //@Column(nullable = false)
    //private List<Integer> roomInventoryOverTime;
    //private HashMap<Date, Integer> roomTypeAvailability;
    
    @NotNull(message = "A room type must have availability list")
    @OneToMany
    @JoinColumn(name = "room_type_id") 
    private List<RoomTypeAvailability> roomTypeAvailabilities = new ArrayList<>();


    //Entity relationship atttributes
    @NotNull(message = "roomRates cannot be null")
    @OneToMany(mappedBy = "roomType")
    private List<RoomRate> roomRates;

    public RoomType() {
        //roomTypeAvailabilities = new ArrayList<>();
        roomRates = new ArrayList<>();
    }

    public RoomType(RoomTypeEnum roomTypeName, String description, int size, String bed, int capacity, String amenities, Boolean isEnabled) {
        this.roomTypeName = roomTypeName;
        this.description = description;
        this.size = size;
        this.bed = bed;
        this.capacity = capacity;
        this.amenities = amenities;
        this.isEnabled = isEnabled;
    }

    /*public RoomType(String roomTypeName, String description, int size, String bed, int capacity, String amenities, List<Integer> roomInventoryOverTime) {
    *    this.roomTypeName = roomTypeName;
    *    this.description = description;
    *    this.size = size;
    *    this.bed = bed;
    *    this.capacity = capacity;
    *    this.amenities = amenities;
    *    this.roomInventoryOverTime = roomInventoryOverTime;
    *    this.roomRates = new ArrayList<RoomRate>();
    }*/

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    
    public RoomTypeEnum getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(RoomTypeEnum roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }
    
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<RoomTypeAvailability> getRoomTypeAvailabilities() {
        return roomTypeAvailabilities;
    }

    public void setRoomTypeAvailabilities(List<RoomTypeAvailability> roomTypeAvailabilities) {
        this.roomTypeAvailabilities = roomTypeAvailabilities;
    }

    //public List<Integer> getRoomInventoryOverTime() {
      //  return roomInventoryOverTime;
    //}

    //public void setRoomInventoryOverTime(List<Integer> roomInventoryOverTime) {
      //  this.roomInventoryOverTime = roomInventoryOverTime;
    //}
    
    public List<RoomRate> getRoomRates() {
        return roomRates;
    }

    public void setRoomRates(List<RoomRate> roomRates) {
        this.roomRates = roomRates;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeId != null ? roomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomTypeId fields are not set
        if (!(object instanceof RoomType)) {
            return false;
        }
        RoomType other = (RoomType) object;
        if ((this.roomTypeId == null && other.roomTypeId != null) || (this.roomTypeId != null && !this.roomTypeId.equals(other.roomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomType[ id=" + roomTypeId + " ]";
    }

}
