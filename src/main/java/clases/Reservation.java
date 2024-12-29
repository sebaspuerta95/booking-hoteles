package src.main.java.clases;

import java.util.List;

public class Reservation {

    private Client client;
    private Hotel hotel;
    private List<Room> rooms;
    private String startDate;
    private String endDate;
    private String estimatedTimeOfArrival;

    public Reservation(Client client, Hotel hotel, List<Room> rooms, String startDate, String endDate, String estimatedTimeOfArrival) {
        this.client = client;
        this.hotel = hotel;
        this.rooms = rooms;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public Client getClient() {
        return client;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void printReservation(){
        System.out.println("Cliente: " + client.getFirstname() + " " + client.getLastname());
        System.out.println("Hotel: " + hotel.getNombre());
        System.out.println("Fecha de inicio: " + startDate + " | Fecha de final: " + endDate);
        System.out.println("Hora extimada de llegada: " + estimatedTimeOfArrival);
        System.out.println("Habitaciones: " );
        printRoom();
    }

    public void printRoom(){
        for (int i = 0; i < rooms.size(); i++){
            System.out.println((i+1)
                    + ". Tipo: " + rooms.get(i).getRoomType() + " | "
                    + "Características: " + rooms.get(i).getCharacteristics() + " | "
                    + "Precio: $" + String.format("%.2f", rooms.get(i).getPrice()));
        }
    }
}
