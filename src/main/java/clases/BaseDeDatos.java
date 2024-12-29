package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {

    private static List<Hotel> hoteles;

    public BaseDeDatos() {
        hoteles = new ArrayList<>();
        inicializarHoteles();
    }

    private void inicializarHoteles() {
        String[] ciudades = {"Medellín", "Bogotá", "Cartagena", "Cali"};
        String[] tiposAlojamiento = {"Hotel", "Apartamento", "Cabaña", "Día de Sol"};
        String[] nombresHoteles = {
                "Hotel Primavera", "Hotel Andino", "Hotel Del Mar", "Hotel Estelar",
                "Apartamento Oasis", "Apartamento Central", "Apartamento del Sol", "Apartamento Panorama",
                "Cabaña La Montaña", "Cabaña del Río", "Cabaña Sunset", "Cabaña Paraíso",
                "Día de Sol Caribe", "Día de Sol Tropical", "Día de Sol Fantasía", "Día de Sol Encanto"
        };

        int[] puntuaciones = {3, 4, 5, 3, 4, 5, 4, 3, 5, 4, 3, 5, 4, 3, 5, 4};

        for (int i = 0; i < puntuaciones.length; i++) {

            String ciudad = ciudades[i % ciudades.length];
            String nombre = nombresHoteles[i];
            String tipoAlojamiento = tiposAlojamiento[i % tiposAlojamiento.length];
            int puntuacion = puntuaciones[i];

            Hotel hotel = new Hotel(ciudad, nombre, tipoAlojamiento, puntuacion);
            asignarHabitaciones(hotel, tipoAlojamiento, i);
            hoteles.add(hotel);
        }
    }

    private void asignarHabitaciones(Hotel hotel, String tipoAlojamiento, int index) {
        if (tipoAlojamiento.equalsIgnoreCase("Día de Sol")) {
            hotel.setHabitaciones(obtenerHabitacionesDiaDeSol());
        } else {
            hotel.setHabitaciones(obtenerHabitacionesGenerales(index));
        }
    }

    private List<Room> obtenerHabitacionesDiaDeSol() {
        List<Room> habitaciones = new ArrayList<>();
        habitaciones.add(new Room(
                "Día de Sol",
                25.0,
                "Natación, cancha de tennis, senderismo, almuerzo incluido.",
                50
        ));
        return habitaciones;
    }

    private List<Room> obtenerHabitacionesGenerales(int indice) {
        List<Room> habitaciones = new ArrayList<>();
        habitaciones.add(new Room(
                "Sencilla",
                50.0 + indice,
                "1 cama individual, TV, aire acondicionado",
                5
        ));
        habitaciones.add(new Room(
                "Doble",
                80.0 + indice,
                "2 camas individuales, TV, aire acondicionado",
                3
        ));
        habitaciones.add(new Room(
                "Suite",
                150.0 + indice,
                "1 cama king, jacuzzi, vista al mar",
                2
        ));
        habitaciones.add(new Room(
                "Familiar",
                120.0 + indice,
                "3 camas, cocina, sala de estar",
                4
        ));
        habitaciones.add(new Room(
                "Lujo",
                200.0 + indice,
                "Suite presidencial, jacuzzi, terraza",
                1
        ));
        return habitaciones;
    }

    public static List<Hotel> getHoteles() {
        return hoteles;
    }

}
