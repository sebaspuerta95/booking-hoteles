package src.main.java.clases;

public class Room {

    private String roomType;
    private double price;
    private String characteristics;
    private int availability;

    public Room(String roomType, double price, String characteristics, int availability) {

        this.roomType = roomType;
        this.price = price;
        this.characteristics = characteristics;
        this.availability = availability;

    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public int getAvailability() {
        return availability;
    }

}
