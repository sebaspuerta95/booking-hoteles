package src.main.java.utils;

public abstract class ReservationProcess {

    public final void processReservation() {

        validateIdentity();
        selectDetails();
        confirmReservation();

    }

    protected abstract void validateIdentity();
    protected abstract void selectDetails();
    protected abstract void confirmReservation();

}