package src.main.java.utils;

import src.main.java.classes.Client;
import src.main.java.classes.Hotel;
import src.main.java.classes.Room;

import java.util.ArrayList;
import java.util.List;

public class CreateReservation extends ReservationProcess{

    private static final UserInteractionService userInteractionService = UserInteractionService.getInstance();
    private static final HotelQueryService hotelQueryService = HotelQueryService.getInstance();
    private static List<Hotel> hotelsList = Database.getInstance().getHotelsList();

    private List<Hotel> availableHotels;
    private Hotel selectedHotel;
    private Room selectedRoom;
    private String startDate;
    private String endDate;
    private int adultsNumber;
    private int childrenNumber;
    private int desiredNumberOfRoomsToBook;

    public CreateReservation (){}

    @Override
    protected void validateIdentity() {
        availableHotels = null;
        while (availableHotels == null) {
            String desiredCity = userInteractionService.requestStringToUser("Ingrese la ciudad donde desea alojarse: ");
            String desiredAccommodationType = userInteractionService.requestStringToUser("Ingrese el tipo de alojamiento (Hotel, Apartamento, Finca o Día de Sol): ");
            startDate = userInteractionService.requestStringToUser("Ingrese la fecha de inicio de su estadía (yyyy-MM-dd): ");
            endDate = userInteractionService.requestStringToUser("Ingrese la fecha final de su estadía (yyyy-MM-dd): ");
            adultsNumber = userInteractionService.requestIntegerToUser("Ingrese la cantidad de adultos: ");
            childrenNumber = userInteractionService.requestIntegerToUser("Ingrese la cantidad de niños: ");
            desiredNumberOfRoomsToBook = userInteractionService.requestIntegerToUser("Ingrese la cantidad de habitaciones: ");

            availableHotels = hotelQueryService.searchHotelsPerCriteria(hotelsList, desiredCity, desiredAccommodationType, startDate, endDate, adultsNumber, childrenNumber, desiredNumberOfRoomsToBook);

            if (availableHotels.isEmpty()) {
                System.out.println("No se encontraron hoteles que cumplan con los criterios.");
                return;
            }
        }

    }

    @Override
    protected void selectDetails() {
        selectedHotel = userInteractionService.selectHotelToBook("Seleccione el número del hotel que desea reservar:", availableHotels);
        List<Room> availableRooms = hotelQueryService.confirmAvailableRooms(selectedHotel, startDate, endDate, adultsNumber, childrenNumber, desiredNumberOfRoomsToBook);

        if (availableRooms.isEmpty()) {
            System.out.println("No se encontraron habitaciones disponibles para las fechas seleccionadas.");
            return;
        }

        selectedRoom = userInteractionService.selectRoomToBook("Seleccione el número de la habitación que desea reservar:", availableRooms);
    }

    @Override
    protected void confirmReservation() {
        Client client = userInteractionService.requestClientsInformation();
        String estimatedTimeOfArrival  = userInteractionService.requestStringToUser("Hora aproximada de llegada: ");

        List<Room> habitacionesSeleccionadas = new ArrayList<>();
        habitacionesSeleccionadas.add(selectedRoom);

        selectedHotel.generateReservation(client, habitacionesSeleccionadas, startDate, endDate, estimatedTimeOfArrival );
        System.out.println("Reserva realizada con éxito.");
    }

}
