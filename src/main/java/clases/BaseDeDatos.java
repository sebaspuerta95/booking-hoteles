package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {

    private static List<Hotel> hoteles;

    public BaseDeDatos() {
        hoteles = new ArrayList<>();

        String[] ciudades = {"Medellín", "Bogotá", "Cartagena", "Cali"};
        String[] tiposAlojamiento = {"Hotel", "Apartamento", "Cabaña", "Día de Sol"};
        String[] nombresHoteles = {
                "Hotel Primavera", "Hotel Andino", "Hotel Del Mar", "Hotel Estelar",
                "Apartamento Oasis", "Apartamento Central", "Apartamento del Sol", "Apartamento Panorama",
                "Cabaña La Montaña", "Cabaña del Río", "Cabaña Sunset", "Cabaña Paraíso",
                "Día de Sol Caribe", "Día de Sol Tropical", "Día de Sol Fantasía", "Día de Sol Encanto"
        };

        int[] puntuaciones = {3, 4, 5, 3, 4, 5, 4, 3, 5, 4, 3, 5, 4, 3, 5, 4};

        for (int i = 0; i < 16; i++) {
            String ciudad = ciudades[i % ciudades.length];
            String nombre = nombresHoteles[i];
            String tipoAlojamiento = tiposAlojamiento[i % tiposAlojamiento.length];
            int puntuacion = puntuaciones[i];

            Hotel hotel = new Hotel(ciudad, nombre, tipoAlojamiento, puntuacion);

            if (tipoAlojamiento.equalsIgnoreCase("Día de Sol")) {
                List<Habitacion> opcionDiaDeSol = new ArrayList<>();
                opcionDiaDeSol.add(new Habitacion(
                        "Día de Sol",
                        25.0,
                        "Natación, cancha de tennis, senderismo, almuerzo incluido.",
                        50
                ));
                hotel.setHabitaciones(opcionDiaDeSol);
            } else {
                List<Habitacion> habitaciones = new ArrayList<>();
                habitaciones.add(new Habitacion(
                        "Sencilla",
                        50.0 + i,
                        "1 cama individual, TV, aire acondicionado",
                        5
                ));
                habitaciones.add(new Habitacion(
                        "Doble",
                        80.0 + i,
                        "2 camas individuales, TV, aire acondicionado",
                        3
                ));
                habitaciones.add(new Habitacion(
                        "Suite",
                        150.0 + i,
                        "1 cama king, jacuzzi, vista al mar",
                        2
                ));
                habitaciones.add(new Habitacion(
                        "Familiar",
                        120.0 + i,
                        "3 camas, cocina, sala de estar",
                        4
                ));
                habitaciones.add(new Habitacion(
                        "Lujo",
                        200.0 + i,
                        "Suite presidencial, jacuzzi, terraza",
                        1
                ));

                hotel.setHabitaciones(habitaciones);
            }

            hoteles.add(hotel);
        }
    }

    public static List<Hotel> getHoteles() {
        return hoteles;
    }

}