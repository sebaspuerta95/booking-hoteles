package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {

    private String ciudad;
    private String nombre;
    private String tipoAlojamiento;
    private int puntuacion;
    private List<Room> habitaciones;
    private List<Reservation> reservations;

    public Hotel(String ciudad, String nombre, String tipoAlojamiento, int puntuacion) {
        this.ciudad = ciudad;
        this.nombre = nombre;
        this.tipoAlojamiento = tipoAlojamiento;
        this.puntuacion = puntuacion;
        this.habitaciones = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void generarReserva(Client client, List<Room> room, String fechaInicio, String fechaFinal, String horaAproxLlegada) {

        Reservation nuevaReservation = new Reservation(client, this, room, fechaInicio, fechaFinal, horaAproxLlegada);
        reservations.add(nuevaReservation);
        // habitacion.setDisponibilidad(habitacion.getDisponibilidad() - 1);
        System.out.println("Reserva generada con éxito para el cliente: " + client.getFirstname() + " " + client.getLastname());

    }

    public void actualizarReserva(String email, String fechaNacimiento) {
        Reservation reservation = validarIdentidadCliente(email, fechaNacimiento);
        esReservaNula(reservation);

        // Interactuar con el usuario para actualizar la reserva
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
                cambiarHabitacion(reservation, scanner);
                break;
            case 2 :
                removerReserva(reservation);
                break;
            case 3 :
                System.out.println("Operación cancelada.");
                break;
            default :
                System.out.println("Opción inválida.");
                break;
        }
    }

    private Reservation validarIdentidadCliente(String email, String fechaNacimiento) {
        for (Reservation r : reservations){
            if (coincideConReserva(r, email, fechaNacimiento)) {
                return r;
            }
        }
        return null;
    }

    private boolean coincideConReserva (Reservation r, String email, String fechaNacimiento) {
        return r.getClient().getEmail().equalsIgnoreCase(email) && r.getClient().getDateOfBirth().equals(fechaNacimiento);
    }

    private void esReservaNula(Reservation reservation) {
        if (reservation == null) {
            System.out.println("Aún no hay reservas a tu nombre.");
        }
    }

    private void removerReserva (Reservation reservation){
        System.out.println("Reserva actual cancelada. Por favor, realiza una nueva reserva.");
        reservations.remove(reservation);
    }

    private void cambiarHabitacion(Reservation reservation, Scanner scanner){
        mostrarHabitacionesReservadas(reservation);

        int habitacionIndex = solicitarHabitacionACambiar(reservation, scanner);

        Room roomActual = reservation.getRooms().get(habitacionIndex);

        mostrarOpcionesDeHabitaciones();

        int nuevaHabitacionIndex = scanner.nextInt() - 1;
        if (esOpcionNuevaHabitacionValida(nuevaHabitacionIndex)) {
            System.out.println("Opción inválida.");
            return;
        }

        Room nuevaRoom = habitaciones.get(nuevaHabitacionIndex);
        reservation.getRooms().remove(roomActual);
        reservation.getRooms().add(nuevaRoom);
        System.out.println("Reserva actualizada con éxito.");

    }

    private void mostrarHabitacionesReservadas(Reservation reservation) {
        System.out.println("Habitaciones reservadas:");
        for (int i = 0; i < reservation.getRooms().size(); i++) {
            reservation.printRoom();
        }
    }

    private int solicitarHabitacionACambiar (Reservation reservation, Scanner scanner) {
        System.out.println("¿Cuál habitación deseas cambiar?");
        int habitacionIndex = scanner.nextInt() - 1;

        if (opcionInvalida(habitacionIndex, reservation)) {
            System.out.println("Opción inválida.");
            return solicitarHabitacionACambiar(reservation, scanner);
        }
        return habitacionIndex;
    }

    private boolean opcionInvalida (int habitacionIndex, Reservation reservation){
        return habitacionIndex < 0 || habitacionIndex >= reservation.getRooms().size();
    }

    private void mostrarOpcionesDeHabitaciones(){
        System.out.println("Selecciona una nueva habitación:");
        for (int i = 0; i < habitaciones.size(); i++) {
            if (estaDisponible(i)) {
                System.out.println((i+1)
                        + ". Tipo: " + habitaciones.get(i).getRoomType() + " | "
                        + "Características: " + habitaciones.get(i).getCharacteristics() + " | "
                        + "Precio: $" + String.format("%.2f", habitaciones.get(i).getPrice()));
            }
        }
    }

    private boolean estaDisponible(int i){
        return habitaciones.get(i).getAvailability() > 0;
    }

    private boolean esOpcionNuevaHabitacionValida(int nuevaHabitacionIndex){
        return nuevaHabitacionIndex < 0 || nuevaHabitacionIndex >= habitaciones.size() ||
                habitaciones.get(nuevaHabitacionIndex).getAvailability() <= 0;
    }

    public void setHabitaciones(List<Room> habitaciones) {
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

    public List<Room> getHabitaciones() {
        return habitaciones;
    }

}
