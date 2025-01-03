package src.main.java.utils.chainOfResponsibility;

import src.main.java.classes.Reservation;

public class EmailHandler extends ValidationHandler{

    @Override
    public Reservation handle(Request request, Reservation reservation) {
        if (validate(request, reservation)) {
            return super.handle(request, reservation);
        }
        return reservation;
    }

    @Override
    protected boolean validate(Request request, Reservation reservation) {
        return reservation.getClient().getEmail().equalsIgnoreCase(request.getEmail());
    }

}
