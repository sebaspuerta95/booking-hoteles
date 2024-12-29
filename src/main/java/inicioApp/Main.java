package src.main.java.inicioApp;

import src.main.java.clases.*;
import src.main.java.utils.Database;
import src.main.java.utils.UserInteractionService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final ServicioBusqueda servicioBusqueda = new ServicioBusqueda();
    private static final Database DATABASE = new Database();
    private static final List<Hotel> hoteles = DATABASE.getHotelsList();
    private static Hotel hotelSeleccionado;

    public static void main(String[] args) {
        boolean salir = false;

        System.out.println("Bienvenido a Booking Hoteles!");
        while (!salir) {
            int opcion = UserInteractionService.askMenuOption("""
                    \nSeleccione una opción:
                    1. Buscar hoteles y reservar
                    2. Actualizar reservation
                    3. Salir
                    Ingrese su opción:\s""", 1, 3);

            switch (opcion) {
                case 1 -> buscarYReservarHotel();
                case 2 -> actualizarReserva();
                case 3 -> {
                    System.out.println("Gracias por usar Booking Hoteles. ¡Hasta pronto!");
                    salir = true;
                }
            }
        }
    }

    private static void buscarYReservarHotel() {
        String ciudad = UserInteractionService.requestStringToUser("Ingrese la ciudad donde desea alojarse: ");
        String tipoAlojamiento = UserInteractionService.requestStringToUser("Ingrese el tipo de alojamiento (Hotel, Apartamento, Finca o Día de Sol): ");
        String fechaInicio = UserInteractionService.requestStringToUser("Ingrese la fecha de inicio de su estadía (yyyy-MM-dd): ");
        String fechaFinal = UserInteractionService.requestStringToUser("Ingrese la fecha final de su estadía (yyyy-MM-dd): ");
        int adultos = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de adultos: ");
        int ninos = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de niños: ");
        int habitaciones = UserInteractionService.requestIntegerToUser("Ingrese la cantidad de habitaciones: ");

        List<Hotel> availableHotels = servicioBusqueda.buscarHoteles(hoteles, ciudad, tipoAlojamiento, fechaInicio, fechaFinal, adultos, ninos, habitaciones);

        if (availableHotels.isEmpty()) {
            System.out.println("No se encontraron hoteles que cumplan con los criterios.");
            return;
        }

        hotelSeleccionado = UserInteractionService.selectHotelToBook("Seleccione el número del hotel que desea reservar:", availableHotels);
        List<Room> availableRooms = servicioBusqueda.confirmarHabitaciones(hotelSeleccionado, fechaInicio, fechaFinal, adultos, ninos, habitaciones);

        if (availableRooms.isEmpty()) {
            System.out.println("No se encontraron habitaciones disponibles para las fechas seleccionadas.");
            return;
        }

        Room roomSeleccionada = UserInteractionService.selectRoomToBook("Seleccione el número de la habitación que desea reservar:", availableRooms);

        Client client = UserInteractionService.requestClientsInformation();
        String horaAproxLlegada  = UserInteractionService.requestStringToUser("Hora aproximada de llegada: ");

        List<Room> habitacionesSeleccionadas = new ArrayList<>();
        habitacionesSeleccionadas.add(roomSeleccionada);

        hotelSeleccionado.generateReservation(client, habitacionesSeleccionadas, fechaInicio, fechaFinal, horaAproxLlegada );
        System.out.println("Reserva realizada con éxito.");
    }

    private static void actualizarReserva() {
        String email = UserInteractionService.requestStringToUser("Para actualizar tu reservation actual, primero debemos validar tu identidad. \nIngresa tu email: ");
        String fechaNacimiento = UserInteractionService.requestStringToUser("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");

        if (hotelSeleccionado == null) {
            System.out.println("No hay reservas realizadas aún.");
            return;
        }

        hotelSeleccionado.updateReservation(email, fechaNacimiento);
    }
}
