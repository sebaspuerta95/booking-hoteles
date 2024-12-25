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

    public void generarReserva(Cliente cliente, List<Habitacion> habitacion, String fechaInicio, String fechaFinal, String horaAproxLlegada) {

        Reserva nuevaReserva = new Reserva(cliente, this, habitacion, fechaInicio, fechaFinal, horaAproxLlegada);
        reservas.add(nuevaReserva);
        // habitacion.setDisponibilidad(habitacion.getDisponibilidad() - 1);
        System.out.println("Reserva generada con éxito para el cliente: " + cliente.getNombre());

    }

    public void actualizarReserva(String email, String fechaNacimiento) {
        Reserva reserva = null;

        // Validación de la identidad del cliente.
        for (Reserva r : reservas){
            if (r.getCliente().getEmail().equalsIgnoreCase(email) &&
                    r.getCliente().getFechaNacimiento().equals(fechaNacimiento)) {
                reserva = r;
                break;
            }
        }

        if (reserva == null) {
            System.out.println("Aún no hay reservas a tu nombre.");
        }

        // Interactuar con el usuario para actualizar la reserva
        System.out.println("Reserva encontrada: ");
        System.out.println(reserva);

        System.out.println("¿Deseas cambiar la habitación o el alojamiento?");
        System.out.println("1. Cambiar habitación");
        System.out.println("2. Cambiar alojamiento");
        System.out.println("3. Cancelar");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1 :
                System.out.println("Habitaciones reservadas:");
                for (int i = 0; i < reserva.getHabitaciones().size(); i++) {
                    System.out.println((i + 1) + ". " + reserva.getHabitaciones().get(i));
                }
                System.out.println("¿Cuál habitación deseas cambiar?");
                int habitacionIndex = scanner.nextInt() - 1;

                if (habitacionIndex < 0 || habitacionIndex >= reserva.getHabitaciones().size()) {
                    System.out.println("Opción inválida.");
                    return;
                }

                Habitacion habitacionActual = reserva.getHabitaciones().get(habitacionIndex);
                System.out.println("Selecciona una nueva habitación:");
                for (int i = 0; i < habitaciones.size(); i++) {
                    if (habitaciones.get(i).getDisponibilidad() > 0) {
                        System.out.println((i + 1) + ". " + habitaciones.get(i));
                    }
                }
                int nuevaHabitacionIndex = scanner.nextInt() - 1;

                if (nuevaHabitacionIndex < 0 || nuevaHabitacionIndex >= habitaciones.size() ||
                        habitaciones.get(nuevaHabitacionIndex).getDisponibilidad() <= 0) {
                    System.out.println("Opción inválida.");
                    return;
                }

                Habitacion nuevaHabitacion = habitaciones.get(nuevaHabitacionIndex);
                reserva.getHabitaciones().remove(habitacionActual);
                reserva.getHabitaciones().add(nuevaHabitacion);
                System.out.println("Reserva actualizada con éxito.");

                break;
            case 2 :
                System.out.println("Reserva actual cancelada. Por favor, realiza una nueva reserva.");
                reservas.remove(reserva);
                break;
            case 3 :
                System.out.println("Operación cancelada.");
                break;
            default :
                System.out.println("Opción inválida.");
                break;
        }
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
