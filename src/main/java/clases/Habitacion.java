package src.main.java.clases;

public class Habitacion {

    private String tipo;
    private double precio;
    private String caracteristicas;
    private int disponibilidad;

    public Habitacion(String tipo, double precio, String caracteristicas, int disponibilidad) {

        this.tipo = tipo;
        this.precio = precio;
        this.caracteristicas = caracteristicas;
        this.disponibilidad = disponibilidad;

    }
    /**
     public double calcularPrecioTotal(int diasEstadia, double descuentoOAumento, int numeroDeHabitaciones) {

     double precioBase = precio * diasEstadia * numeroDeHabitaciones;
     double ajuste = precioBase * (descuentoOAumento / 100);
     return precioBase + ajuste;

     } */

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

}
