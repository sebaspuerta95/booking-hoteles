package src.main.java.utils.chainOfResponsibility;

import src.main.java.classes.Reservation;

// Chain of responsibility implementation.
public abstract class ValidationHandler {
    private ValidationHandler nextHandler;

    public void setNextHandler(ValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Reservation handle(Request request, Reservation reservation) {
        if (nextHandler != null) {
            return nextHandler.handle(request, reservation);
        }
        return reservation;
    }

    protected abstract boolean validate(Request request, Reservation reservation);

}
