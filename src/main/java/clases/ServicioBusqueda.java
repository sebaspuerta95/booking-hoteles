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
                double precioFinalConDescuento = calcularDescuento(precioHabitacion, fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);

                System.out.println("Nombre del establecimiento: " + hotel.getNombre());
                System.out.println("Tipo de alojamiento: " + hotel.getTipoAlojamiento());
                System.out.println("Ciudad del establecimiento: " + hotel.getCiudad());
                System.out.println("Puntuación: " + hotel.getPuntuacion());
                System.out.println("Precio por habitación: $" + precioHabitacion);
                System.out.println("Precio total de la estadía: $" + precioFinalConDescuento);
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
    private double calcularDescuento(double precioHabitacion, String fechaInicioAlojamiento, String fechaFinalAlojamiento, int numeroDeHabitacionesAReservar) {
        // Convertir las fechas a LocalDate
        LocalDate inicio = LocalDate.parse(fechaInicioAlojamiento);
        LocalDate fin = LocalDate.parse(fechaFinalAlojamiento);

        // Calcular el número total de días de estadía
        long diasEstadia = ChronoUnit.DAYS.between(inicio, fin);

        // Calcular el precio base total
        double precioTotal = precioHabitacion * diasEstadia * numeroDeHabitacionesAReservar;

        // Validar los días y aplicar ajustes
        int diaInicio = inicio.getDayOfMonth();
        int diaFin = fin.getDayOfMonth();

        if (diaFin > 25) {
            precioTotal *= 1.15;
        } else if (diaInicio >= 10 && diaFin <= 15) {
            precioTotal *= 1.10;
        } else if (diaInicio >= 5 && diaFin <= 10) {
            precioTotal *= 0.92;
        }

        return precioTotal;
    }

    public List<Habitacion> confirmarHabitaciones(Hotel hotel, String fechaInicioAlojamiento,String fechaFinalAlojamiento, int numeroDeAdultos, int numeroDeNiños, int numeroDeHabitacionesAReservar) {

        List<Habitacion> resultadosHabitaciones = new ArrayList<>();
        double precioFinalConDescuento;

        System.out.println("Estas son las habitaciones disponibles en el hotel " + hotel.getNombre());

        for (Habitacion habitacion : hotel.getHabitaciones()) {
            resultadosHabitaciones.add(habitacion);

            precioFinalConDescuento = calcularDescuento(habitacion.getPrecio(), fechaInicioAlojamiento, fechaFinalAlojamiento, numeroDeHabitacionesAReservar);
            System.out.println("Tipo: " + habitacion.getTipo()
                    + " | Características: " + habitacion.getCaracteristicas()
                    + " | Precio por habitación: $" + habitacion.getPrecio()
                    + " | Precio total: $" + precioFinalConDescuento);
        }

        return resultadosHabitaciones;
    }

}
