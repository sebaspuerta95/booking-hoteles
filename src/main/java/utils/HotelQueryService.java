package src.main.java.utils;

import src.main.java.classes.Hotel;
import src.main.java.classes.Room;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelQueryService {
    private static HotelQueryService hqsUniqueInstance;

    private HotelQueryService (){}

    public static HotelQueryService getInstance(){
        if (hqsUniqueInstance == null) {
            hqsUniqueInstance = new HotelQueryService();
        }
        return hqsUniqueInstance;
    }


    public List<Hotel> searchHotelsPerCriteria(List<Hotel> hotelsList, String desiredCity, String desiredAccommodationType, String startDate, String endDate, int adultsNumber, int childrenNumber, int desiredNumberOfRoomsToBook) {
        List<Hotel> hotelsListResult = filterHotelsByCriteria(hotelsList, desiredCity, desiredAccommodationType);

        obtainRoomTypesPerHotel(hotelsListResult, startDate, endDate, desiredNumberOfRoomsToBook);

        return hotelsListResult;
    }

    private List<Hotel> filterHotelsByCriteria(List<Hotel> hotelsList, String desiredCity, String desiredAccommodationType) {
        List<Hotel> hotelsListResult = new ArrayList<>();
        for (Hotel hotel : hotelsList) {
            if (hotel.getCity().equalsIgnoreCase(desiredCity) || hotel.getAccommodationType().equalsIgnoreCase(desiredAccommodationType)) {
                hotelsListResult.add(hotel);
            }
        }
        return hotelsListResult;
    }

    private void obtainRoomTypesPerHotel(List<Hotel> hotelsListResult, String startDate, String endDate, int desiredNumberOfRoomsToBook) {
        for (Hotel hotel : hotelsListResult) {
            Room cheapestRoom = obtainCheapestRoomPerHotel(hotel);

            printRoomsWithPrice(hotel, cheapestRoom, startDate, endDate, desiredNumberOfRoomsToBook);
        }
    }

    private Room obtainCheapestRoomPerHotel(Hotel hotel){
        Room cheapestRoom = null;
        for (Room room : hotel.getRoomTypes()) {
            if (room.getAvailability() > 0) {
                cheapestRoom = filterCheapestRoom(room, cheapestRoom);
            }
        }
        return cheapestRoom;
    }

    private Room filterCheapestRoom(Room room, Room cheapestRoom){
        if (cheapestRoom == null) {
            cheapestRoom = room;
        } else if (room.getPrice() < cheapestRoom.getPrice()) {
            cheapestRoom = room;
        }
        return cheapestRoom;
    }

    private void printRoomsWithPrice(Hotel hotel, Room cheapestRoom, String startDate,
                                     String endDate, int desiredNumberOfRoomsToBook) {
        if (cheapestRoom != null) {
            double cheapestRoomPrice = cheapestRoom.getPrice();
            double finalPriceWithModifications = obtainRoomPriceWithDates(cheapestRoomPrice, startDate, endDate, desiredNumberOfRoomsToBook);

            printHotelInformation(hotel, cheapestRoomPrice, finalPriceWithModifications);
        } else {
            System.out.println("El hotel " + hotel.getHotelName() + " no tiene habitaciones disponibles para las fechas solicitadas.");
            System.out.println("-----------------------------------------");
        }
    }

    private void printHotelInformation(Hotel hotel, double cheapestRoomPrice, double finalPriceWithModofications) {
        System.out.println("Nombre del establecimiento: " + hotel.getHotelName());
        System.out.println("Tipo de alojamiento: " + hotel.getAccommodationType());
        System.out.println("Ciudad del establecimiento: " + hotel.getCity());
        System.out.println("Puntuación: " + hotel.getHotelScore());
        System.out.println("Precio por habitación: $" + String.format("%.2f", cheapestRoomPrice));
        System.out.println("Precio total de la estadía: $" + String.format("%.2f", finalPriceWithModofications));
        System.out.println("-----------------------------------------");
    }

    private double obtainRoomPriceWithDates(double cheapestRoomPrice, String startDate, String endDate, int desiredNumberOfRoomsToBook) {
        LocalDate start = transformIntoDate(startDate);
        LocalDate end = transformIntoDate(endDate);

        long stayDays = ChronoUnit.DAYS.between(start, end);

        double totalPriceWithoutModifications = cheapestRoomPrice * stayDays * desiredNumberOfRoomsToBook;

        int startDayOfMonth = start.getDayOfMonth();
        int endDayOfMonth = end.getDayOfMonth();

        return calculateFinalPrice(totalPriceWithoutModifications, startDayOfMonth, endDayOfMonth);
    }

    private LocalDate transformIntoDate(String date) {
        return LocalDate.parse(date);
    }

    private double calculateFinalPrice(double totalPriceWithoutModifications, int startDayOfMonth, int endDayOfMonth) {
        final double FEE_END_MONTH = 1.15;
        final double FEE_HALF_MONTH = 1.10;
        final double DISCOUNT_START_MONTH = 0.92;

        if (isEndOfMonth(endDayOfMonth)) {
            return modifiedFinalPrice(totalPriceWithoutModifications, FEE_END_MONTH);
        }
        if (isHalfMonth(startDayOfMonth, endDayOfMonth)) {
            return modifiedFinalPrice(totalPriceWithoutModifications, FEE_HALF_MONTH);
        }
        if (isStartOfMonth(startDayOfMonth, endDayOfMonth)) {
            return modifiedFinalPrice(totalPriceWithoutModifications, DISCOUNT_START_MONTH);
        }
        return totalPriceWithoutModifications;
    }

    private boolean isEndOfMonth (int endDayOfMonth){
        if (endDayOfMonth > 25) {
            return true;
        }
        return false;
    }

    private boolean isHalfMonth (int startDayOfMonth, int endDayOfMonth) {
        if (startDayOfMonth >= 10 && endDayOfMonth <= 15) {
            return true;
        }
        return false;
    }

    private boolean isStartOfMonth (int startDayOfMonth, int endDayOfMonth) {
        if (startDayOfMonth >= 5 && endDayOfMonth <= 10) {
            return true;
        }
        return false;
    }

    private double modifiedFinalPrice(double totalPriceWithoutModifications, double priceModifier){
        return totalPriceWithoutModifications*priceModifier;
    }

    public List<Room> confirmAvailableRooms(Hotel selectedHotel, String startDate, String endDate, int adultsNumber, int childrenNumber, int desiredNumberOfRoomsToBook) {

        List<Room> availableRoomsResult = new ArrayList<>();
        double finalPriceWithModifications;

        System.out.println("Estas son las habitaciones disponibles en el hotel " + selectedHotel.getHotelName());

        for (Room room : selectedHotel.getRoomTypes()) {
            availableRoomsResult.add(room);

            finalPriceWithModifications = obtainRoomPriceWithDates(room.getPrice(), startDate, endDate, desiredNumberOfRoomsToBook);
            System.out.println("Tipo: " + room.getRoomType()
                    + " | Características: " + room.getCharacteristics()
                    + " | Precio por habitación: $" + String.format("%.2f", room.getPrice())
                    + " | Precio total: $" + String.format("%.2f", finalPriceWithModifications));
        }

        return availableRoomsResult;
    }

}