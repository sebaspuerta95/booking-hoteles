package src.main.java.clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ServicioBusqueda {

    public List<Hotel> buscarHoteles(List<Hotel> hoteles, String ciudad, String tipoAlojamiento, String fechaInicioAlojamiento, String fechaFinalAlojamiento, int numeroDeAdultos, int numeroDeNiños, int numeroDeHabitacionesAReservar) {
        List<Hotel> resultados = filtroDeHotelesPorCriterio(hoteles, ciudad, tipoAlojamiento);

        obtenerHabitacionesPorHotel(resultados, fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);

        return resultados;
    }

    private List<Hotel> filtroDeHotelesPorCriterio(List<Hotel> hoteles, String ciudad, String tipoAlojamiento) {
        List<Hotel> resultados = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().equalsIgnoreCase(ciudad) || hotel.getTipoAlojamiento().equalsIgnoreCase(tipoAlojamiento)) {
                resultados.add(hotel);
            }
        }
        return resultados;
    }

    private void obtenerHabitacionesPorHotel(List<Hotel> resultados, String fechaInicioAlojamiento, String fechaFinalAlojamiento, int numeroDeHabitacionesAReservar) {
        for (Hotel hotel : resultados) {
            Habitacion habitacionMasBarata = obtenerHabitacionMasBarataPorHotel(hotel);

            mostrarHabitacionConPrecio(hotel, habitacionMasBarata, fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);
        }
    }

    private Habitacion obtenerHabitacionMasBarataPorHotel (Hotel hotel){
        Habitacion habitacionMasBarata = null;
        for (Habitacion habitacion : hotel.getHabitaciones()) {
            if (habitacion.getDisponibilidad() > 0) {
                habitacionMasBarata = filtrarPorHabitaciones(habitacion, habitacionMasBarata);
            }
        }
        return habitacionMasBarata;
    }

    private Habitacion filtrarPorHabitaciones (Habitacion habitacion, Habitacion habitacionMasBarata){
        if (habitacionMasBarata == null) {
            habitacionMasBarata = habitacion;
        } else if (habitacion.getPrecio() < habitacionMasBarata.getPrecio()) {
            habitacionMasBarata = habitacion;
        }
        return habitacionMasBarata;
    }

    private void mostrarHabitacionConPrecio(Hotel hotel, Habitacion habitacionMasBarata, String fechaInicioAlojamiento,
                                            String fechaFinalAlojamiento, int numeroDeHabitacionesAReservar) {
        if (habitacionMasBarata != null) {
            double precioHabitacion = habitacionMasBarata.getPrecio();
            double precioFinalConDescuento = obtenerPrecioPorFecha(precioHabitacion, fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);

            imprimirInformacionDelHotel(hotel, precioHabitacion, precioFinalConDescuento);
        } else {
            System.out.println("El hotel " + hotel.getNombre() + " no tiene habitaciones disponibles para las fechas solicitadas.");
            System.out.println("-----------------------------------------");
        }
    }

    private void imprimirInformacionDelHotel(Hotel hotel, double precioHabitacion, double precioFinalConDescuento) {
        System.out.println("Nombre del establecimiento: " + hotel.getNombre());
        System.out.println("Tipo de alojamiento: " + hotel.getTipoAlojamiento());
        System.out.println("Ciudad del establecimiento: " + hotel.getCiudad());
        System.out.println("Puntuación: " + hotel.getPuntuacion());
        System.out.println("Precio por habitación: $" + String.format("%.2f", precioHabitacion));
        System.out.println("Precio total de la estadía: $" + String.format("%.2f", precioFinalConDescuento));
        System.out.println("-----------------------------------------");
    }

    private double obtenerPrecioPorFecha(double precioHabitacion, String fechaInicioAlojamiento, String fechaFinalAlojamiento, int numeroDeHabitacionesAReservar) {
        LocalDate inicio = transformarAFecha(fechaInicioAlojamiento);
        LocalDate fin = transformarAFecha(fechaFinalAlojamiento);

        long diasEstadia = ChronoUnit.DAYS.between(inicio, fin);

        double precioTotal = precioHabitacion * diasEstadia * numeroDeHabitacionesAReservar;

        int diaInicio = inicio.getDayOfMonth();
        int diaFin = fin.getDayOfMonth();

        return calcularPrecioFinal(precioTotal, diaInicio, diaFin);
    }

    private LocalDate transformarAFecha(String fecha) {
        return LocalDate.parse(fecha);
    }

    private double calcularPrecioFinal(double precioTotal, int diaInicio, int diaFin) {
        final double RECARGO_LATE = 1.15;
        final double DESCUENTO_MEDIO_MES = 1.10;
        final double DESCUENTO_INICIO_MES = 0.92;

        if (isEndOfMonth(diaFin)) {
            return precioFinalModificado(precioTotal, RECARGO_LATE);
        }
        if (isHalfMonth(diaInicio, diaFin)) {
            return precioFinalModificado(precioTotal, DESCUENTO_MEDIO_MES);
        }
        if (isStartOfMonth(diaInicio, diaFin)) {
            return precioFinalModificado(precioTotal, DESCUENTO_INICIO_MES);
        }
        return precioTotal;
    }

    private boolean isEndOfMonth (int diaFin){
        if (diaFin > 25) {
            return true;
        }
        return false;
    }

    private boolean isHalfMonth (int diaInicio, int diaFin) {
        if (diaInicio >= 10 && diaFin <= 15) {
            return true;
        }
        return false;
    }

    private boolean isStartOfMonth (int diaInicio, int diaFin) {
        if (diaInicio >= 5 && diaFin <= 10) {
            return true;
        }
        return false;
    }

    private double precioFinalModificado (double precioFinal, double recargo){
        return precioFinal*recargo;
    }

    public List<Habitacion> confirmarHabitaciones(Hotel hotel, String fechaInicioAlojamiento,String fechaFinalAlojamiento, int numeroDeAdultos, int numeroDeNiños, int numeroDeHabitacionesAReservar) {

        List<Habitacion> resultadosHabitaciones = new ArrayList<>();
        double precioFinalConDescuento;

        System.out.println("Estas son las habitaciones disponibles en el hotel " + hotel.getNombre());

        for (Habitacion habitacion : hotel.getHabitaciones()) {
            resultadosHabitaciones.add(habitacion);

            precioFinalConDescuento = obtenerPrecioPorFecha(habitacion.getPrecio(), fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);
            System.out.println("Tipo: " + habitacion.getTipo()
                    + " | Características: " + habitacion.getCaracteristicas()
                    + " | Precio por habitación: $" + String.format("%.2f", habitacion.getPrecio())
                    + " | Precio total: $" + String.format("%.2f", precioFinalConDescuento));
        }

        return resultadosHabitaciones;
    }

}