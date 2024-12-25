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

}
