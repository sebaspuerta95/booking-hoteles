package src.main.java.clases;

import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {

    // Esta clase servirá como base de datos para almacenar hoteles y habitaciones.

    private List<Hotel> hoteles;

    public BaseDeDatos() {
        hoteles = new ArrayList<>();
        // Agregar hoteles a la lista (datos simulados o cargados de una fuente)
    }

    public List<Hotel> getHoteles() {
        return hoteles;
    }

}
