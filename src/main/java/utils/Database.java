package src.main.java.utils;

import src.main.java.classes.Hotel;
import src.main.java.classes.Room;

import java.util.ArrayList;
import java.util.List;

// Singleton pattern implementation.
public class Database {

    private static Database dbUniqueInstance;
    private static List<Hotel> hotelsList;

    private Database() {
        hotelsList = new ArrayList<>();
        initializeHotels();
    }

    public static Database getInstance() {
        if (dbUniqueInstance == null) {
            dbUniqueInstance = new Database();
        }
        return dbUniqueInstance;
    }

    private void initializeHotels() {
        String[] citiesList = {"Medellín", "Bogotá", "Cartagena", "Cali"};
        String[] accommodationTypesList = {"Hotel", "Apartamento", "Cabaña", "Día de Sol"};
        String[] hotelsNamesList = {
                "Hotel Primavera", "Hotel Andino", "Hotel Del Mar", "Hotel Estelar",
                "Apartamento Oasis", "Apartamento Central", "Apartamento del Sol", "Apartamento Panorama",
                "Cabaña La Montaña", "Cabaña del Río", "Cabaña Sunset", "Cabaña Paraíso",
                "Día de Sol Caribe", "Día de Sol Tropical", "Día de Sol Fantasía", "Día de Sol Encanto"
        };

        int[] scoresList = {3, 4, 5, 3, 4, 5, 4, 3, 5, 4, 3, 5, 4, 3, 5, 4};

        for (int i = 0; i < scoresList.length; i++) {

            String city = citiesList[i % citiesList.length];
            String hotelName = hotelsNamesList[i];
            String accommodationType = accommodationTypesList[i % accommodationTypesList.length];
            int hotelScore = scoresList[i];

            Hotel hotel = new Hotel(city, hotelName, accommodationType, hotelScore);
            assignRooms(hotel, accommodationType, i);
            hotelsList.add(hotel);
        }
    }

    private void assignRooms(Hotel hotel, String accommodationType, int index) {
        if (accommodationType.equalsIgnoreCase("Día de Sol")) {
            hotel.setRoomTypes(getDayPassCharacteristics());
        } else {
            hotel.setRoomTypes(obtainTypesOfRooms(index));
        }
    }

    private List<Room> getDayPassCharacteristics() {
        List<Room> roomTypes = new ArrayList<>();
        roomTypes.add(new Room(
                "Día de Sol",
                25.0,
                "Natación, cancha de tennis, senderismo, almuerzo incluido.",
                50
        ));
        return roomTypes;
    }

    private List<Room> obtainTypesOfRooms(int index) {
        List<Room> roomsTypes = new ArrayList<>();
        roomsTypes.add(new Room(
                "Sencilla",
                50.0 + index,
                "1 cama individual, TV, aire acondicionado",
                5
        ));
        roomsTypes.add(new Room(
                "Doble",
                80.0 + index,
                "2 camas individuales, TV, aire acondicionado",
                3
        ));
        roomsTypes.add(new Room(
                "Suite",
                150.0 + index,
                "1 cama king, jacuzzi, vista al mar",
                2
        ));
        roomsTypes.add(new Room(
                "Familiar",
                120.0 + index,
                "3 camas, cocina, sala de estar",
                4
        ));
        roomsTypes.add(new Room(
                "Lujo",
                200.0 + index,
                "Suite presidencial, jacuzzi, terraza",
                1
        ));
        return roomsTypes;
    }

    public static List<Hotel> getHotelsList() {
        return hotelsList;
    }

}
