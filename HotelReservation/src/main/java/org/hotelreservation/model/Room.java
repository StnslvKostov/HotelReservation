package org.hotelreservation.model;

public class Room {

   private int roomNumber;
   private String roomType;
   private int pricePerNight;
   private int cancellationFee;
   private int maximumOccupancy;
   private String amenities;

    public Room() {
    }

    public Room(int roomNumber, String roomType, int pricePerNight, int cancellationFee, int maximumOccupancy, String amenities) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.cancellationFee = cancellationFee;
        this.maximumOccupancy = maximumOccupancy;
        this.amenities = amenities;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(int cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    public int getMaximumOccupancy() {
        return maximumOccupancy;
    }

    public void setMaximumOccupancy(int maximumOccupancy) {
        this.maximumOccupancy = maximumOccupancy;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return
                "Room number: " + this.roomNumber + "\n"
                        + "Room Type: " + this.roomType + "\n"
                        + "Price Per Night" + this.pricePerNight + "\n"
                        + "Cancellation Fee: " + this.cancellationFee + "\n"
                        + "Maximum Occupancy: " + this.maximumOccupancy + "\n"
                        + "amenities: " + this.amenities + "\n"
                + "=====================================================";

    }
}
