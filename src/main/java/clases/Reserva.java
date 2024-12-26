package src.main.java.clases;

import java.util.List;

public class Reserva {

    private Cliente cliente;
    private Hotel hotel;
    private List<Habitacion> habitacion;
    private String fechaInicio;
    private String fechaFinal;
    private String horaAproxLlegada;

    public Reserva(Cliente cliente, Hotel hotel, List<Habitacion> habitacion, String fechaInicio, String fechaFinal, String horaAproxLlegada) {
        this.cliente = cliente;
        this.hotel = hotel;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.horaAproxLlegada = horaAproxLlegada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Habitacion> getHabitaciones() {
        return habitacion;
    }

    public void imprimirReserva(){
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Hotel: " + hotel);
        System.out.println("Fecha de inicio: " + fechaInicio + " | Fecha de final: " + fechaFinal);
        System.out.println("Habitaciones: \n" + imprimirHabitacion());
    }

    private String imprimirHabitacion(){
        for (int i =0; i < habitacion.size(); i++){
            System.out.println("Habitación " + (i+1) + ": \n"
                    + "Tipo: " + habitacion.get(i).getTipo()
                    + "Características: " + habitacion.get(i).getCaracteristicas()
                    + "Precio: $" + habitacion.get(i).getPrecio());
        }
        return null;
    }

}
