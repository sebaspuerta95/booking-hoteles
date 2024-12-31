package src.main.java.utils;

import src.main.java.classes.Client;
import src.main.java.classes.Hotel;
import src.main.java.classes.Room;

import java.util.List;
import java.util.Scanner;

public class UserInteractionService {
    private static UserInteractionService uisUniqueInstance;
    private static final Scanner scanner = new Scanner(System.in);

    private UserInteractionService(){}

    public static UserInteractionService getInstance(){
        if (uisUniqueInstance == null) {
            uisUniqueInstance = new UserInteractionService();
        }
        return uisUniqueInstance;
    }

    public static String requestStringToUser(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int requestIntegerToUser(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public static Hotel selectHotelToBook(String message, List<Hotel> availableHotels) {
        System.out.println(message);
        for (int i = 0; hotelsListOptionLimit(i, availableHotels); i++) {
            printHotelsOptionsList(i, availableHotels);
        }

        int selection = requestIntegerToUser("Ingrese el número de su elección: ") - 1;
        int hotelsOptionsSize = availableHotels.size();
        if (isSelectionValid(selection, hotelsOptionsSize)) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return selectHotelToBook(message, availableHotels);
        }

        return availableHotels.get(selection);
    }

    private static boolean hotelsListOptionLimit(int i, List<Hotel> hotelsOptions){
        return i < hotelsOptions.size();
    }

    private static void printHotelsOptionsList(int i, List<Hotel> hotelsOptions){
        Hotel hotel = hotelsOptions.get(i);
        System.out.println((i + 1) + ". " + hotel.getHotelName());
    }

    private static boolean isSelectionValid(int selection, int hotelsOptionsSize){
        return selection < 0 || selection >= hotelsOptionsSize;
    }

    public static Room selectRoomToBook(String message, List<Room> availableRooms) {
        System.out.println(message);
        for (int i = 0; roomsListOptionLimit(i, availableRooms); i++) {
            printRoomsOptionList(i, availableRooms);
        }

        int selectin = requestIntegerToUser("Ingrese el número de su elección: ") - 1;
        int roomsOptionsSize = availableRooms.size();
        if (isSelectionValid(selectin, roomsOptionsSize)) {
            System.out.println("Selección inválida. Inténtelo de nuevo.");
            return selectRoomToBook(message, availableRooms);
        }

        return availableRooms.get(selectin);
    }

    private static boolean roomsListOptionLimit(int i, List<Room> roomsOptions){
        return i < roomsOptions.size();
    }

    private static void printRoomsOptionList(int i, List<Room> roomsOptions) {
        Room room = roomsOptions.get(i);
        System.out.println((i + 1) + ". " + room.getRoomType());
    }

    public static Client requestClientsInformation() {
        scanner.nextLine();
        String clientFirstname = requestStringToUser("Ingrese su nombre: ");
        String clientLastname = requestStringToUser("Ingrese su apellido: ");
        String clientDateOfBirth = requestStringToUser("Ingrese su fecha de nacimiento (yyyy-MM-dd): ");
        String clientEmail = requestStringToUser("Ingrese su email: ");
        String clientNationality = requestStringToUser("Ingrese su nacionalidad: ");
        String clientPhoneNumber = requestStringToUser("Ingrese su número de teléfono: ");

        return new Client(
                clientFirstname,
                clientLastname,
                clientDateOfBirth,
                clientEmail,
                clientNationality,
                clientPhoneNumber);
    }

    public static int askMenuOption(String message, int minimumOption, int maximumOption) {
        System.out.print(message);
        int menuOption = scanner.nextInt();
        scanner.nextLine();
        if (isValidMenuOption(menuOption, minimumOption, maximumOption)) {
            System.out.println("Opción inválida. Inténtelo de nuevo.");
            return askMenuOption(message, minimumOption, maximumOption);
        }
        return menuOption;
    }

    private static boolean isValidMenuOption(int menuOption, int minimumOption, int maximumOption) {
        return menuOption < minimumOption || menuOption > maximumOption;
    }

}
