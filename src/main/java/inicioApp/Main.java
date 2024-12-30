package src.main.java.inicioApp;

import src.main.java.clases.*;
import src.main.java.utils.Database;
import src.main.java.utils.HotelQueryService;
import src.main.java.utils.UserInteractionService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final HotelQueryService hotelQueryService = new HotelQueryService();
    private static final Database DATABASE = Database.getInstance();
    private static final List<Hotel> hotelsList = DATABASE.getHotelsList();
    private static Hotel selectedHotel;

    public static void main(String[] args) {
        boolean exitFromSystem = false;

        System.out.println("Bienvenido a Booking Hoteles!");
        while (!exitFromSystem) {
            int option = UserInteractionService.askMenuOption("""
                    \nSeleccione una opción:
                    1. Buscar hoteles y reservar
                    2. Actualizar reservation
                    3. Salir
                    Ingrese su opción:\s""", 1, 3);

            switch (option) {
                case 1 -> searchAndBookHotel();
                case 2 -> updateExistingReservation();
                case 3 -> {
                    System.out.println("Gracias por usar Booking Hoteles. ¡Hasta pronto!");
                    exitFromSystem = true;
                }
            }
        }
    }

    private static void searchAndBookHotel() {
        String desiredCity = UserInteractionService.requestStringToUser("Ingrese la ciudad donde desea alojarse: ");
        String desiredAccommodationType = UserInteractionService.requestStringToUser("Ingrese el tipo de alojamiento (Hotel, Apartamento, Finca o Día de Sol): ");
        String startDate = UserInteractionService.requestStringToUser("Ingrese la fecha de inicio de su estadía (yyyy-MM-dd): ");
        String endDate = UserInteractionService.requestStringToUser("Ingrese la fecha final de su estadía (yyyy-MM-dd): ");
        int adultsNumber = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de adultos: ");
        int childrenNumber = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de niños: ");
        int desiredNumberOfRoomsToBook = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de habitaciones: ");

        List<Hotel> availableHotels = hotelQueryService.searchHotelsPerCriteria(hotelsList, desiredCity, desiredAccommodationType, startDate, endDate, adultsNumber, childrenNumber, desiredNumberOfRoomsToBook);

        if (availableHotels.isEmpty()) {
            System.out.println("No se encontraron hoteles que cumplan con los criterios.");
            return;
        }

        selectedHotel = UserInteractionService.selectHotelToBook("Seleccione el número del hotel que desea reservar:", availableHotels);
        List<Room> availableRooms = hotelQueryService.confirmAvailableRooms(selectedHotel, startDate, endDate, adultsNumber, childrenNumber, desiredNumberOfRoomsToBook);

        if (availableRooms.isEmpty()) {
            System.out.println("No se encontraron habitaciones disponibles para las fechas seleccionadas.");
            return;
        }

        Room selectedRoom = UserInteractionService.selectRoomToBook("Seleccione el número de la habitación que desea reservar:", availableRooms);

        Client client = UserInteractionService.requestClientsInformation();
        String estimatedTimeOfArrival  = UserInteractionService.requestStringToUser("Hora aproximada de llegada: ");

        List<Room> habitacionesSeleccionadas = new ArrayList<>();
        habitacionesSeleccionadas.add(selectedRoom);

        selectedHotel.generateReservation(client, habitacionesSeleccionadas, startDate, endDate, estimatedTimeOfArrival );
        System.out.println("Reserva realizada con éxito.");
    }

    private static void updateExistingReservation() {
        String email = UserInteractionService.requestStringToUser("Para actualizar tu reservation actual, primero debemos validar tu identidad. \nIngresa tu email: ");
        String dateOfBirth = UserInteractionService.requestStringToUser("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");

        if (selectedHotel == null) {
            System.out.println("No hay reservas realizadas aún.");
            return;
        }

        selectedHotel.updateReservation(email, dateOfBirth);
    }
}
