/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class RoomType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomTypeId;
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
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
    @NotNull(message = "RoomInventoryOverTime cannot be null")
    @Column(nullable = false)
    private List<Integer> roomInventoryOverTime;
    @NotNull(message = "PublishedRate cannot be null")
    @Column(nullable = false)
    private RoomRate publishedRate;
    @NotNull(message = "NormalRate cannot be null")
    @Column(nullable = false)
    private RoomRate normalRate;
    private RoomRate peakRate;
    private RoomRate promotionRate;

    public RoomType() {
    }

    public RoomType(String name, String description, int size, String bed, int capacity, String amenities, List<Integer> roomInventoryOverTime, RoomRate publishedRate, RoomRate normalRate, RoomRate peakRate, RoomRate promotionRate) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.bed = bed;
        this.capacity = capacity;
        this.amenities = amenities;
        this.roomInventoryOverTime = roomInventoryOverTime;
        this.publishedRate = publishedRate;
        this.normalRate = normalRate;
        this.peakRate = peakRate;
        this.promotionRate = promotionRate;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Integer> getRoomInventoryOverTime() {
        return roomInventoryOverTime;
    }

    public void setRoomInventoryOverTime(List<Integer> roomInventoryOverTime) {
        this.roomInventoryOverTime = roomInventoryOverTime;
    }

    public RoomRate getPublishedRate() {
        return publishedRate;
    }

    public void setPublishedRate(RoomRate publishedRate) {
        this.publishedRate = publishedRate;
    }

    public RoomRate getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(RoomRate normalRate) {
        this.normalRate = normalRate;
    }

    public RoomRate getPeakRate() {
        return peakRate;
    }

    public void setPeakRate(RoomRate peakRate) {
        this.peakRate = peakRate;
    }

    public RoomRate getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(RoomRate promotionRate) {
        this.promotionRate = promotionRate;
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
