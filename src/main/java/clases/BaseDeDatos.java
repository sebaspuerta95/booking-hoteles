package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {

    private List<Hotel> hoteles;

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

        for (int i = 0; i < 16; i++) {
            String ciudad = ciudades[i % ciudades.length];
            String nombre = nombresHoteles[i];
            String tipoAlojamiento = tiposAlojamiento[i % tiposAlojamiento.length];
            int puntuacion = 3 + (int) (Math.random() * 3);

            Hotel hotel = new Hotel(ciudad, nombre, tipoAlojamiento, puntuacion);

            if (tipoAlojamiento.equalsIgnoreCase("Día de Sol")) {
                List<Habitacion> opcionDiaDeSol = new ArrayList<>();
                opcionDiaDeSol.add(new Habitacion("Dia de Sol", 25.0, "Natación, cancha de tennis, senderismo, almuerzo incluido.", 50));
                hotel.setHabitaciones(opcionDiaDeSol);
                hoteles.add(hotel);
            } else {
                List<Habitacion> habitaciones = new ArrayList<>();
                habitaciones.add(new Habitacion("Sencilla", 50.0 + i * 2, "1 cama individual, TV, aire acondicionado", 5));
                habitaciones.add(new Habitacion("Doble", 80.0 + i * 2, "2 camas individuales, TV, aire acondicionado", 3));
                habitaciones.add(new Habitacion("Suite", 150.0 + i * 2, "1 cama king, jacuzzi, vista al mar", 2));
                habitaciones.add(new Habitacion("Familiar", 120.0 + i * 2, "3 camas, cocina, sala de estar", 4));
                habitaciones.add(new Habitacion("Lujo", 200.0 + i * 2, "Suite presidencial, jacuzzi, terraza", 1));

                hotel.setHabitaciones(habitaciones);
                hoteles.add(hotel);
            }
        }
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

}
