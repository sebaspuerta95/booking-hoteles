package src.main.java.clases;

import java.util.List;
import java.util.Scanner;

public class InteraccionUsuario {
    private static final Scanner scanner = new Scanner(System.in);

    public static String solicitarCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static int solicitarEntero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public static Hotel seleccionarHotel(String mensaje, List<Hotel> opciones) {
        System.out.println(mensaje);
        for (int i = 0; limiteDeOpcionesHoteles(i, opciones); i++) {
            imprimirHotel(i, opciones);
        }

        int seleccion = solicitarEntero("Ingrese el número de su elección: ") - 1;
        int opcionesSize = opciones.size();
        if (esValidaLaSeleccion(seleccion, opcionesSize)) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return seleccionarHotel(mensaje, opciones);
        }

        return opciones.get(seleccion);
    }

    private static boolean limiteDeOpcionesHoteles(int i, List<Hotel> opciones){
        return i < opciones.size();
    }

    private static void imprimirHotel (int i, List<Hotel> opciones){
        Hotel hotel = opciones.get(i);
        System.out.println((i + 1) + ". " + hotel.getHotelName());
    }

    private static boolean esValidaLaSeleccion(int seleccion, int opcionesSize){
        return seleccion < 0 || seleccion >= opcionesSize;
    }

    public static Room seleccionarHabitacion(String mensaje, List<Room> opciones) {
        System.out.println(mensaje);
        for (int i = 0; limiteDeOpcionesHabitaciones(i, opciones); i++) {
            imprimitHabitacion(i, opciones);
        }

        int seleccion = solicitarEntero("Ingrese el número de su elección: ") - 1;
        int opcionesSize = opciones.size();
        if (esValidaLaSeleccion(seleccion, opcionesSize)) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return seleccionarHabitacion(mensaje, opciones);
        }

        return opciones.get(seleccion);
    }

    private static boolean limiteDeOpcionesHabitaciones(int i, List<Room> opciones){
        return i < opciones.size();
    }

    private static void imprimitHabitacion (int i, List<Room> opciones) {
        Room room = opciones.get(i);
        System.out.println((i + 1) + ". " + room.getRoomType());
    }

    public static Client solicitarCliente() {
        scanner.nextLine();
        String nombre = solicitarCadena("Ingrese su nombre: ");
        String apellido = solicitarCadena("Ingrese su apellido: ");
        String fechaNacimiento = solicitarCadena("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");
        String email = solicitarCadena("Ingrese su email: ");
        String nacionalidad = solicitarCadena("Ingrese su nacionalidad: ");
        String telefono = solicitarCadena("Ingrese su número de teléfono: ");

        return new Client(nombre, apellido, fechaNacimiento, email, nacionalidad, telefono);
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        System.out.print(mensaje);
        int opcion = scanner.nextInt();
        scanner.nextLine();
        if (validarOpcion(opcion, min, max)) {
            System.out.println("Opción inválida. Inténtelo de nuevo.");
            return solicitarOpcion(mensaje, min, max);
        }
        return opcion;
    }

    private static boolean validarOpcion(int opcion, int min, int max) {
        return opcion < min || opcion > max;
    }

}

