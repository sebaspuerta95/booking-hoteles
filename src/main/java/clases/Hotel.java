package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {

    private String ciudad;
    private String nombre;
    private String tipoAlojamiento;
    private int puntuacion;
    private List<Habitacion> habitaciones;
    private List<Reserva> reservas;

    public Hotel(String ciudad, String nombre, String tipoAlojamiento, int puntuacion) {
        this.ciudad = ciudad;
        this.nombre = nombre;
        this.tipoAlojamiento = tipoAlojamiento;
        this.puntuacion = puntuacion;
        this.habitaciones = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void generarReserva(Client client, List<Habitacion> habitacion, String fechaInicio, String fechaFinal, String horaAproxLlegada) {

        Reserva nuevaReserva = new Reserva(client, this, habitacion, fechaInicio, fechaFinal, horaAproxLlegada);
        reservas.add(nuevaReserva);
        // habitacion.setDisponibilidad(habitacion.getDisponibilidad() - 1);
        System.out.println("Reserva generada con éxito para el cliente: " + client.getFirstname() + " " + client.getLastname());

    }

    public void actualizarReserva(String email, String fechaNacimiento) {
        Reserva reserva = validarIdentidadCliente(email, fechaNacimiento);
        esReservaNula(reserva);

        // Interactuar con el usuario para actualizar la reserva
        System.out.println("Reserva encontrada: ");
        reserva.imprimirReserva();

        System.out.println("¿Deseas cambiar la habitación o el alojamiento?");
        System.out.println("1. Cambiar habitación");
        System.out.println("2. Cambiar alojamiento");
        System.out.println("3. Cancelar");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1 :
                cambiarHabitacion(reserva, scanner);
                break;
            case 2 :
                removerReserva(reserva);
                break;
            case 3 :
                System.out.println("Operación cancelada.");
                break;
            default :
                System.out.println("Opción inválida.");
                break;
        }
    }

    private Reserva validarIdentidadCliente(String email, String fechaNacimiento) {
        for (Reserva r : reservas){
            if (coincideConReserva(r, email, fechaNacimiento)) {
                return r;
            }
        }
        return null;
    }

    private boolean coincideConReserva (Reserva r, String email, String fechaNacimiento) {
        return r.getCliente().getEmail().equalsIgnoreCase(email) && r.getCliente().getDateOfBirth().equals(fechaNacimiento);
    }

    private void esReservaNula(Reserva reserva) {
        if (reserva == null) {
            System.out.println("Aún no hay reservas a tu nombre.");
        }
    }

    private void removerReserva (Reserva reserva){
        System.out.println("Reserva actual cancelada. Por favor, realiza una nueva reserva.");
        reservas.remove(reserva);
    }

    private void cambiarHabitacion(Reserva reserva, Scanner scanner){
        mostrarHabitacionesReservadas(reserva);

        int habitacionIndex = solicitarHabitacionACambiar(reserva, scanner);

        Habitacion habitacionActual = reserva.getHabitaciones().get(habitacionIndex);

        mostrarOpcionesDeHabitaciones();

        int nuevaHabitacionIndex = scanner.nextInt() - 1;
        if (esOpcionNuevaHabitacionValida(nuevaHabitacionIndex)) {
            System.out.println("Opción inválida.");
            return;
        }

        Habitacion nuevaHabitacion = habitaciones.get(nuevaHabitacionIndex);
        reserva.getHabitaciones().remove(habitacionActual);
        reserva.getHabitaciones().add(nuevaHabitacion);
        System.out.println("Reserva actualizada con éxito.");

    }

    private void mostrarHabitacionesReservadas(Reserva reserva) {
        System.out.println("Habitaciones reservadas:");
        for (int i = 0; i < reserva.getHabitaciones().size(); i++) {
            reserva.imprimirHabitacion();
        }
    }

    private int solicitarHabitacionACambiar (Reserva reserva, Scanner scanner) {
        System.out.println("¿Cuál habitación deseas cambiar?");
        int habitacionIndex = scanner.nextInt() - 1;

        if (opcionInvalida(habitacionIndex, reserva)) {
            System.out.println("Opción inválida.");
            return solicitarHabitacionACambiar(reserva, scanner);
        }
        return habitacionIndex;
    }

    private boolean opcionInvalida (int habitacionIndex, Reserva reserva){
        return habitacionIndex < 0 || habitacionIndex >= reserva.getHabitaciones().size();
    }

    private void mostrarOpcionesDeHabitaciones(){
        System.out.println("Selecciona una nueva habitación:");
        for (int i = 0; i < habitaciones.size(); i++) {
            if (estaDisponible(i)) {
                System.out.println((i+1)
                        + ". Tipo: " + habitaciones.get(i).getTipo() + " | "
                        + "Características: " + habitaciones.get(i).getCaracteristicas() + " | "
                        + "Precio: $" + String.format("%.2f", habitaciones.get(i).getPrecio()));
            }
        }
    }

    private boolean estaDisponible(int i){
        return habitaciones.get(i).getDisponibilidad() > 0;
    }

    private boolean esOpcionNuevaHabitacionValida(int nuevaHabitacionIndex){
        return nuevaHabitacionIndex < 0 || nuevaHabitacionIndex >= habitaciones.size() ||
                habitaciones.get(nuevaHabitacionIndex).getDisponibilidad() <= 0;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoAlojamiento() {
        return tipoAlojamiento;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

}
