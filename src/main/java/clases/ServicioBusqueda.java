package src.main.java.clases;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ServicioBusqueda {

    public List<Hotel> buscarHoteles(List<Hotel> hoteles, String ciudad, String tipoAlojamiento, String fechaInicioAlojamiento, String fechaFinalAlojamiento, int numeroDeAdultos, int numeroDeNiños, int numeroDeHabitacionesAReservar) {

        // 1. Comparar la ciudad y el tipo de alojamiento con los datos de los hoteles.
        List<Hotel> resultados = new ArrayList<>();
        for (Hotel hotel : hoteles) {
            if (hotel.getCiudad().equalsIgnoreCase(ciudad) || hotel.getTipoAlojamiento().equalsIgnoreCase(tipoAlojamiento)) {
                resultados.add(hotel);
            }
        }

        // 2. Para cada hotel, verificar qué habitaciones hay disponibles.

        for (Hotel hotel : resultados) {
            Habitacion habitacionMasBarata = null;

            // 3. Buscar la habitación más económica de cada hotel.
            for (Habitacion habitacion : hotel.getHabitaciones()) {
                if (habitacion.getDisponibilidad() > 0) {
                    if (habitacionMasBarata == null) {
                        // Asignar la primera habitación disponible como referencia
                        habitacionMasBarata = habitacion;
                    } else if (habitacion.getPrecio() < habitacionMasBarata.getPrecio()) {
                        // Comparar el precio con la habitación más barata encontrada
                        habitacionMasBarata = habitacion;
                    }
                }
            }

            // 4. Calcular el precio y mostrar los detalles de la habitación más barata.

            if (habitacionMasBarata != null) {
                double precioHabitacion = habitacionMasBarata.getPrecio();
                double precioFinalConDescuento = obtenerPrecioPorFecha(precioHabitacion, fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);

                System.out.println("Nombre del establecimiento: " + hotel.getNombre());
                System.out.println("Tipo de alojamiento: " + hotel.getTipoAlojamiento());
                System.out.println("Ciudad del establecimiento: " + hotel.getCiudad());
                System.out.println("Puntuación: " + hotel.getPuntuacion());
                System.out.println("Precio por habitación: $" + String.format("%.2f", precioHabitacion));
                System.out.println("Precio total de la estadía: $" + String.format("%.2f", precioFinalConDescuento));
                System.out.println("-----------------------------------------");
            } else {
                System.out.println("El hotel " + hotel.getNombre() + " no tiene habitaciones disponibles para las fechas solicitadas.");
                System.out.println("-----------------------------------------");
            }

        }

        // 5. Devolver una lista de hoteles que cumplan con los criterios de búsqueda para que el usuario pueda elegir una opción.
        return resultados;
    }

    // Este es un método auxiliar para calcular el descuento/aumento según las fechas. Es privado para que no sea usado desde otra clase.
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
