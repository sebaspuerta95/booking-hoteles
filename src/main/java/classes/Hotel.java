package src.main.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void updateReservation(String email, String dateOfBirth) {
        Reservation reservation = validateClientsIdentity(email, dateOfBirth);
        isReservationNull(reservation);

        System.out.println("Reserva encontrada: ");
        reservation.printReservation();

        System.out.println("¿Deseas cambiar la habitación o el alojamiento?");
        System.out.println("1. Cambiar habitación");
        System.out.println("2. Cambiar alojamiento");
        System.out.println("3. Cancelar");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1 :
                updateRoom(reservation, scanner);
                break;
            case 2 :
                removeReservation(reservation);
                break;
            case 3 :
                System.out.println("Operación cancelada.");
                break;
            default :
                System.out.println("Opción inválida.");
                break;
        }
    }

    private Reservation validateClientsIdentity(String email, String dateOfBirth) {
        for (Reservation r : reservations){
            if (matchesWithReservation(r, email, dateOfBirth)) {
                return r;
            }
        }
        return null;
    }

    private boolean matchesWithReservation(Reservation r, String email, String dateOfBirth) {
        return r.getClient().getEmail().equalsIgnoreCase(email) && r.getClient().getDateOfBirth().equals(dateOfBirth);
    }

    private void isReservationNull(Reservation reservation) {
        if (reservation == null) {
            System.out.println("Aún no hay reservas a tu nombre.");
        }
    }

    private void removeReservation(Reservation reservation){
        System.out.println("Reserva actual cancelada. Por favor, realiza una nueva reserva.");
        reservations.remove(reservation);
    }

    private void updateRoom(Reservation reservation, Scanner scanner){
        showBookedReservation(reservation);

        int roomIndex = askRoomToUpdate(reservation, scanner);

        Room currentBookedRoom = reservation.getBookedRooms().get(roomIndex);

        printAvailableRooms();

        int newRoomIndex = scanner.nextInt() - 1;
        if (isNewOptionValid(newRoomIndex)) {
            System.out.println("Opción inválida.");
            return;
        }

        Room newUpdatedRoom = roomTypes.get(newRoomIndex);
        reservation.getBookedRooms().remove(currentBookedRoom);
        reservation.getBookedRooms().add(newUpdatedRoom);
        System.out.println("Reserva actualizada con éxito.");

    }

    private void showBookedReservation(Reservation reservation) {
        System.out.println("Habitaciones reservadas:");
        for (int i = 0; i < reservation.getBookedRooms().size(); i++) {
            reservation.printRoom();
        }
    }

    private int askRoomToUpdate(Reservation reservation, Scanner scanner) {
        System.out.println("¿Cuál habitación deseas cambiar?");
        int roomIndex = scanner.nextInt() - 1;

        if (isOptionValid(roomIndex, reservation)) {
            System.out.println("Opción inválida.");
            return askRoomToUpdate(reservation, scanner);
        }
        return roomIndex;
    }

    private boolean isOptionValid(int roomIndex, Reservation reservation){
        return roomIndex < 0 || roomIndex >= reservation.getBookedRooms().size();
    }

    private void printAvailableRooms(){
        System.out.println("Selecciona una nueva habitación:");
        for (int i = 0; i < roomTypes.size(); i++) {
            if (isRoomAvailable(i)) {
                System.out.println((i+1)
                        + ". Tipo: " + roomTypes.get(i).getRoomType() + " | "
                        + "Características: " + roomTypes.get(i).getCharacteristics() + " | "
                        + "Precio: $" + String.format("%.2f", roomTypes.get(i).getPrice()));
            }
        }
    }

    private boolean isRoomAvailable(int i){
        return roomTypes.get(i).getAvailability() > 0;
    }

    private boolean isNewOptionValid(int newRoomIndex){
        return newRoomIndex < 0 || newRoomIndex >= roomTypes.size() ||
                roomTypes.get(newRoomIndex).getAvailability() <= 0;
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

}
