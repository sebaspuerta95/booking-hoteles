package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;

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

    public void generarReserva(Cliente cliente, Habitacion habitacion, String fechaInicio, String fechaFinal, String horaAproxLlegada) {

        Reserva nuevaReserva = new Reserva(cliente, this, habitacion, fechaInicio, fechaFinal, horaAproxLlegada);
        reservas.add(nuevaReserva);
        habitacion.setDisponibilidad(habitacion.getDisponibilidad() - 1);
        System.out.println("Reserva generada con éxito para el cliente: " + cliente.getNombre());

    }

    public void actualizarReserva(String email, String fechaNacimiento) {
        // Validar identidad y permitir actualización de reservas
        System.out.println("Actualizando reserva para el cliente con email: " + email);
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
