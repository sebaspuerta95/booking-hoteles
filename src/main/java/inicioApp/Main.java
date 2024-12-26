package src.main.java.inicioApp;

import src.main.java.clases.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final ServicioBusqueda servicioBusqueda = new ServicioBusqueda();
    private static final BaseDeDatos baseDeDatos = new BaseDeDatos();
    private static final List<Hotel> hoteles = baseDeDatos.getHoteles();
    private static Hotel hotelSeleccionado;

    public static void main(String[] args) {
        boolean salir = false;

        System.out.println("Bienvenido a Booking Hoteles!");
        while (!salir) {
            int opcion = InteraccionUsuario.solicitarOpcion("""
                    \nSeleccione una opción:
                    1. Buscar hoteles y reservar
                    2. Actualizar reserva
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
        String ciudad = InteraccionUsuario.solicitarCadena("Ingrese la ciudad donde desea alojarse: ");
        String tipoAlojamiento = InteraccionUsuario.solicitarCadena("Ingrese el tipo de alojamiento (Hotel, Apartamento, Finca o Día de Sol): ");
        String fechaInicio = InteraccionUsuario.solicitarCadena("Ingrese la fecha de inicio de su estadía (yyyy-MM-dd): ");
        String fechaFinal = InteraccionUsuario.solicitarCadena("Ingrese la fecha final de su estadía (yyyy-MM-dd): ");
        int adultos = InteraccionUsuario.solicitarEntero("Ingrese la cantidad de adultos: ");
        int ninos = InteraccionUsuario.solicitarEntero("Ingrese la cantidad de niños: ");
        int habitaciones = InteraccionUsuario.solicitarEntero("Ingrese la cantidad de habitaciones: ");

        List<Hotel> opciones = servicioBusqueda.buscarHoteles(hoteles, ciudad, tipoAlojamiento, fechaInicio, fechaFinal, adultos, ninos, habitaciones);

        if (opciones.isEmpty()) {
            System.out.println("No se encontraron hoteles que cumplan con los criterios.");
            return;
        }

        hotelSeleccionado = InteraccionUsuario.seleccionarHotel("Seleccione el número del hotel que desea reservar:", opciones);
        List<Habitacion> habitacionesDisponibles = servicioBusqueda.confirmarHabitaciones(hotelSeleccionado, fechaInicio, fechaFinal, adultos, ninos, habitaciones);

        if (habitacionesDisponibles.isEmpty()) {
            System.out.println("No se encontraron habitaciones disponibles para las fechas seleccionadas.");
            return;
        }

        Habitacion habitacionSeleccionada = InteraccionUsuario.seleccionarHabitacion("Seleccione el número de la habitación que desea reservar:", habitacionesDisponibles);

        Cliente cliente = InteraccionUsuario.solicitarCliente();
        String horaAproxLlegada  = InteraccionUsuario.solicitarCadena("Hora aproximada de llegada: ");

        List<Habitacion> habitacionesSeleccionadas = new ArrayList<>();
        habitacionesSeleccionadas.add(habitacionSeleccionada);

        hotelSeleccionado.generarReserva(cliente, habitacionesSeleccionadas, fechaInicio, fechaFinal, horaAproxLlegada );
        System.out.println("Reserva realizada con éxito.");
    }

    private static void actualizarReserva() {
        String email = InteraccionUsuario.solicitarCadena("Para actualizar tu reserva actual, primero debemos validar tu identidad. \nIngresa tu email: ");
        String fechaNacimiento = InteraccionUsuario.solicitarCadena("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");

        if (hotelSeleccionado == null) {
            System.out.println("No hay reservas realizadas aún.");
            return;
        }

        hotelSeleccionado.actualizarReserva(email, fechaNacimiento);
    }
}
