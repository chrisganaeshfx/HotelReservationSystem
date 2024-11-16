package util.exceptions.reservation;

public class InvalidReservationException extends Exception {
    public InvalidReservationException() {
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}
