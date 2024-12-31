package src.main.java.classes;

import java.util.List;

public class Reservation {

    private Client client;
    private Hotel hotel;
    private List<Room> bookedRooms;
    private String startDate;
    private String endDate;
    private String estimatedTimeOfArrival;

    public Reservation(Client client, Hotel hotel, List<Room> bookedRooms, String startDate, String endDate, String estimatedTimeOfArrival) {
        this.client = client;
        this.hotel = hotel;
        this.bookedRooms = bookedRooms;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public Client getClient() {
        return client;
    }

    public List<Room> getBookedRooms() {
        return bookedRooms;
    }

    public void printReservation(){
        System.out.println("Cliente: " + client.getFirstname() + " " + client.getLastname());
        System.out.println("Hotel: " + hotel.getHotelName());
        System.out.println("Fecha de inicio: " + startDate + " | Fecha de final: " + endDate);
        System.out.println("Hora extimada de llegada: " + estimatedTimeOfArrival);
        System.out.println("Habitaciones: " );
        printRoom();
    }

    public void printRoom(){
        for (int i = 0; i < bookedRooms.size(); i++){
            System.out.println((i+1)
                    + ". Tipo: " + bookedRooms.get(i).getRoomType() + " | "
                    + "Características: " + bookedRooms.get(i).getCharacteristics() + " | "
                    + "Precio: $" + String.format("%.2f", bookedRooms.get(i).getPrice()));
        }
    }
}
