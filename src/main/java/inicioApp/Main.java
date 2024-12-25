package src.main.java.inicioApp;

import src.main.java.clases.Hotel;
import src.main.java.clases.ServicioBusqueda;
import java.util.List;
import java.util.Scanner;

public class Main {

    static ServicioBusqueda nuevaBusqueda = new ServicioBusqueda();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Bienvenido a Booking Hoteles!");
        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Buscar hoteles y reservar.");
            System.out.println("2. Actualizar reserva");
            System.out.println("3. Salir");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 :
                    buscarYReservarHotel(scanner);
                    break;
                case 2 :
                    //reservationService.updateReservation(scanner);
                    break;
                case 3 :
                    System.out.println("Gracias por usar Booking Hoteles. ¡Hasta pronto!");
                    System.exit(0);
                default :
                    System.out.println("Opción inválida. Inténtelo de nuevo.");
                    break;
            }
        }
    }

    public static void buscarYReservarHotel(Scanner scanner){

        System.out.println("Ingrese la ciudad donde desea alojarse:");
        String ciudad = scanner.nextLine();

        System.out.println("Ingrese el tipo de alojamiento (Hotel, Apartamento, Finca ó Día de Sol):");
        String tipoAlojamiento = scanner.nextLine();

        System.out.println("Ingrese la fecha de inicio de su estadía (yyyy-MM-dd):");
        String fechaInicioAlojamiento = scanner.nextLine();

        System.out.println("Ingrese la fecha final de su estadía (yyyy-MM-dd):");
        String fechaFinalAlojamiento = scanner.nextLine();

        System.out.println("Ingrese la cantidad de adultos:");
        int numeroDeAdultos = scanner.nextInt();

        System.out.println("Ingrese la cantidad de niños:");
        int numeroDeNiños = scanner.nextInt();

        System.out.println("Ingrese la cantidad de habitaciones:");
        int numeroDeHabitacionesAReservar = scanner.nextInt();
        scanner.nextLine();

        List<Hotel> opciones = nuevaBusqueda.buscarHoteles(
                ciudad,
                tipoAlojamiento,
                fechaInicioAlojamiento,
                fechaFinalAlojamiento,
                numeroDeAdultos,
                numeroDeNiños,
                numeroDeHabitacionesAReservar
        );

        if (opciones.isEmpty()) {
            System.out.println("No se encontró ningún hotel que cumpla con los criterios de búsqueda.");
        } else {
            System.out.println("\nSeleccione el número del hotel que desea reservar:");
            for (int i = 0; i < opciones.size(); i++) {
                Hotel hotel = opciones.get(i);
                System.out.println((i + 1) + ". " + hotel.getNombre());
            }

            try {
                int seleccion = scanner.nextInt();
                scanner.nextLine();
                if (seleccion < 1 || seleccion > opciones.size()) {
                    System.out.println("Selección inválida, intente de nuevo.");
                } else {
                    Hotel hotelSeleccionado = opciones.get(seleccion - 1);
                    nuevaBusqueda.confirmarHabitaciones(hotelSeleccionado, fechaInicioAlojamiento, fechaFinalAlojamiento,
                            numeroDeAdultos, numeroDeNiños, numeroDeHabitacionesAReservar);
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Inténtelo de nuevo.");
                scanner.nextLine();
            }
        }

    }

}
