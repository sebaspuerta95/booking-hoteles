package src.main.java.utils;

import src.main.java.classes.Hotel;
import src.main.java.classes.Reservation;
import src.main.java.classes.Room;

import java.util.List;
import java.util.Scanner;

public class UpdateReservation extends ReservationProcess{

    private static final UserInteractionService userInteractionService = UserInteractionService.getInstance();
    private static List<Hotel> hotelsList = Database.getInstance().getHotelsList();

    private Reservation reservation;
    private int option;
    private Hotel myHotel;
    private List<Room> roomTypes;

    public UpdateReservation(){}

    @Override
    protected void validateIdentity() {
        String email = userInteractionService.requestStringToUser("Para actualizar tu reservation actual, primero debemos validar tu identidad. \nIngresa tu email: ");
        String dateOfBirth = userInteractionService.requestStringToUser("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");

        reservation = validateClientsIdentity(email, dateOfBirth);
        isReservationNull(reservation);

        System.out.println("Reserva encontrada: ");
        reservation.printReservation();

        myHotel = reservation.getHotel();
        roomTypes = myHotel.getRoomTypes();
    }

    @Override
    protected void selectDetails() {

        option = userInteractionService.askMenuOption("""
            \n¿Deseas cambiar la habitación o el alojamiento?:
            1. Cambiar habitación
            2. Cambiar alojamiento
            3. Cancelar
            Ingrese su opción:\s""", 1, 3);

    }

    @Override
    protected void confirmReservation() {

        switch (option) {
            case 1 -> {
                Scanner scanner = new Scanner(System.in);
                updateRoom(reservation, scanner);
            }
            case 2 -> removeReservation(reservation);
            case 3 -> System.out.println("Operación cancelada.");
            default -> System.out.println("Opción inválida.");
        }

    }

    private Reservation validateClientsIdentity(String email, String dateOfBirth) {
        for (Hotel hotel : hotelsList) {
            for (Reservation r : hotel.getReservations()) {
                if (matchesWithReservation(r, email, dateOfBirth)) {
                    return r;
                }
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
        List<Reservation> reservationList = reservation.getHotel().getReservations();
        reservationList.remove(reservation);
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

}
