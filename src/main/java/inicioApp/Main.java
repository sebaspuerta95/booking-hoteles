package src.main.java.inicioApp;

import src.main.java.clases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static ServicioBusqueda nuevaBusqueda = new ServicioBusqueda();
    static Scanner scanner = new Scanner(System.in);
    static BaseDeDatos listaHoteles = new BaseDeDatos();
    private static List<Hotel> hoteles = listaHoteles.getHoteles();

    public static void main(String[] args) {

        boolean salir = false;

        System.out.println("Bienvenido a Booking Hoteles!");
        while (!salir) {
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
                    actualizarReserva(scanner);
                    break;
                case 3 :
                    System.out.println("Gracias por usar Booking Hoteles. ¡Hasta pronto!");
                    salir = true;
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
                hoteles,
                ciudad,
                tipoAlojamiento,
                fechaInicioAlojamiento,
                fechaFinalAlojamiento,
                numeroDeAdultos,
                numeroDeNiños,
                numeroDeHabitacionesAReservar
        );

        Hotel hotelSeleccionado = null;
        List<Habitacion> opcionesHabitac = null;
        Habitacion habitacSeleccionada = null;

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
                    hotelSeleccionado = opciones.get(seleccion - 1);
                    opcionesHabitac = nuevaBusqueda.confirmarHabitaciones(
                            hotelSeleccionado,
                            fechaInicioAlojamiento,
                            fechaFinalAlojamiento,
                            numeroDeAdultos,
                            numeroDeNiños,
                            numeroDeHabitacionesAReservar);
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Inténtelo de nuevo.");
                scanner.nextLine();
            }
        }

        if (opcionesHabitac.isEmpty()) {
            System.out.println("No se encontraron habitaciones disponibles para la fecha seleccionada.");
        } else {
            System.out.println("\nSeleccione el número de la habitación que desea reservar:");
            for (int i = 0; i < opcionesHabitac.size(); i++) {
                Habitacion habitacion = opcionesHabitac.get(i);
                System.out.println((i + 1) + ". " + habitacion.getTipo());
            }

            try {
                int seleccion = scanner.nextInt();
                scanner.nextLine();
                if (seleccion < 1 || seleccion > opcionesHabitac.size()) {
                    System.out.println("Selección inválida, intente de nuevo.");
                } else {
                    habitacSeleccionada = opcionesHabitac.get(seleccion - 1);

                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Inténtelo de nuevo.");
                scanner.nextLine();
            }
        }

        System.out.println("Habitación seleccionada exitosamente. Por favor provee la siguiente información:");
        System.out.println("Nombre:");
        String nombre = scanner.nextLine();

        System.out.println("Apellido:");
        String apellido = scanner.nextLine();

        System.out.println("Fecha de nacimiento (yyyy-MM-dd):");
        String fechaNacimiento = scanner.nextLine();

        System.out.println("E-mail:");
        String email = scanner.nextLine();

        System.out.println("Nacionalidad:");
        String nacionalidad = scanner.nextLine();

        System.out.println("Número telefónico:");
        String numTelefonico = scanner.nextLine();

        System.out.println("Hora aproximada de llegada:");
        String horaAproxLlegada = scanner.nextLine();
        scanner.nextLine();

        Cliente nuevoCliente = new Cliente(nombre, apellido, fechaNacimiento, email, nacionalidad, numTelefonico);
        List<Habitacion> habitacionesSeleccionadas = new ArrayList<>();
        habitacionesSeleccionadas.add(habitacSeleccionada);

        hotelSeleccionado.generarReserva(nuevoCliente, habitacionesSeleccionadas, fechaInicioAlojamiento, fechaFinalAlojamiento, horaAproxLlegada);

    }

    private static void actualizarReserva(Scanner scanner) {
        System.out.println("Para actualizar tu reserva actual, primero debemos validar tu identidad. \nIngresa tu email:");
        String email = scanner.next();
        System.out.println("Ingresa tu fecha de nacimiento (YYYY-MM-DD):");
        String fechaNacimiento = scanner.next();

        for (Hotel hotel : hoteles) {
            hotel.actualizarReserva(email, fechaNacimiento);
        }
    }

}
