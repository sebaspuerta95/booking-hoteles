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
        for (int i = 0; i < opciones.size(); i++) {
            Hotel hotel = opciones.get(i);
            System.out.println((i + 1) + ". " + hotel.getNombre());
        }

        int seleccion = solicitarEntero("Ingrese el número de su elección: ") - 1;
        if (seleccion < 0 || seleccion >= opciones.size()) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return seleccionarHotel(mensaje, opciones);
        }

        return opciones.get(seleccion);
    }

    public static Habitacion seleccionarHabitacion(String mensaje, List<Habitacion> opciones) {
        System.out.println(mensaje);
        for (int i = 0; i < opciones.size(); i++) {
            Habitacion habitacion = opciones.get(i);
            System.out.println((i + 1) + ". " + habitacion.getTipo());
        }

        int seleccion = solicitarEntero("Ingrese el número de su elección: ") - 1;
        if (seleccion < 0 || seleccion >= opciones.size()) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return seleccionarHabitacion(mensaje, opciones);
        }

        return opciones.get(seleccion);
    }

    public static Cliente solicitarCliente() {
        scanner.nextLine();
        String nombre = solicitarCadena("Ingrese su nombre: ");
        String apellido = solicitarCadena("Ingrese su apellido: ");
        String fechaNacimiento = solicitarCadena("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");
        String email = solicitarCadena("Ingrese su email: ");
        String nacionalidad = solicitarCadena("Ingrese su nacionalidad: ");
        String telefono = solicitarCadena("Ingrese su número de teléfono: ");

        return new Cliente(nombre, apellido, fechaNacimiento, email, nacionalidad, telefono);
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        System.out.print(mensaje);
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        if (opcion < min || opcion > max) {
            System.out.println("Opción inválida. Inténtelo de nuevo.");
            return solicitarOpcion(mensaje, min, max);
        }
        return opcion;
    }
}

