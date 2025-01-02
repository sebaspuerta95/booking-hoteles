package src.main.java.classes;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String city;
    private String hotelName;
    private String accommodationType;
    private int hotelScore;
    private List<Room> roomTypes;
    private List<Reservation> reservations;

    public Hotel(String city, String hotelName, String accommodationType, int hotelScore) {
        this.city = city;
        this.hotelName = hotelName;
        this.accommodationType = accommodationType;
        this.hotelScore = hotelScore;
        this.roomTypes = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void generateReservation(Client client, List<Room> room, String startDate, String endDate, String estimatedTimeOfArrival) {

        Reservation newReservation = new Reservation(client, this, room, startDate, endDate, estimatedTimeOfArrival);
        reservations.add(newReservation);
        // habitacion.setDisponibilidad(habitacion.getDisponibilidad() - 1);
        System.out.println("Reserva generada con éxito para el cliente: " + client.getFirstname() + " " + client.getLastname());

    }

    public void setRoomTypes(List<Room> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public String getCity() {
        return city;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public int getHotelScore() {
        return hotelScore;
    }

    public List<Room> getRoomTypes() {
        return roomTypes;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

}