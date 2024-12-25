package src.main.java.clases;

public class Reserva {

    private Cliente cliente;
    private Hotel hotel;
    private Habitacion habitacion;
    private String fechaInicio;
    private String fechaFinal;
    private String horaAproxLlegada;

    public Reserva(Cliente cliente, Hotel hotel, Habitacion habitacion, String fechaInicio, String fechaFinal, String horaAproxLlegada) {
        this.cliente = cliente;
        this.hotel = hotel;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.horaAproxLlegada = horaAproxLlegada;
    }

}
