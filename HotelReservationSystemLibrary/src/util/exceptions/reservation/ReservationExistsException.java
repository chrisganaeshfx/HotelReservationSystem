package util.exceptions.reservation;

public class ReservationExistsException extends Exception {
    public ReservationExistsException() {
    }

    public ReservationExistsException(String message) {
        super(message);
    }
}