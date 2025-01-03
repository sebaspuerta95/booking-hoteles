package src.main.java.utils.chainOfResponsibility;

import src.main.java.classes.Reservation;

public class DateOfBirthHandler extends ValidationHandler{

    @Override
    public Reservation handle(Request request, Reservation reservation) {
        if (validate(request, reservation)) {
            return reservation;
        }
        return null;
    }

    @Override
    protected boolean validate(Request request, Reservation reservation) {
        return reservation.getClient().getDateOfBirth().equalsIgnoreCase(request.getDateOfBith());
    }

}
