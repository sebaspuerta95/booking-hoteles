package src.main.java.appStart;

import src.main.java.utils.*;

public class Main {

    private static final UserInteractionService userInteractionService = UserInteractionService.getInstance();
    private static CreateReservation createReservation = new CreateReservation();
    private static UpdateReservation updateReservation = new UpdateReservation();

    public static void main(String[] args) {
        try {
            boolean exitFromSystem = false;

            System.out.println("Bienvenido a Booking Hoteles!");
            while (!exitFromSystem) {
                int option = userInteractionService.askMenuOption("""
                        \nSeleccione una opción:
                        1. Buscar hoteles y reservar.
                        2. Actualizar reserva.
                        3. Salir.
                        Ingrese su opción:\s""", 1, 3);

                switch (option) {
                    case 1 -> searchAndBookHotel();
                    case 2 -> updateExistingReservation();
                    case 3 -> {
                        System.out.println("Gracias por usar Booking Hoteles. ¡Hasta pronto!");
                        exitFromSystem = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchAndBookHotel() {
        try {
            createReservation.processReservation();
        } catch (Exception e) {
            System.err.println("Error al crear la reserva: " + e.getMessage());
        }
    }

    private static void updateExistingReservation() {
        try {
            updateReservation.processReservation();
        } catch (Exception e) {
            System.err.println("Error al actualizar la reserva: " + e.getMessage());
        }
    }

}
