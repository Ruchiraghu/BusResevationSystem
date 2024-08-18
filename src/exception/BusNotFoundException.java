package exception;

public class BusNotFoundException extends Exception{
    public BusNotFoundException() {
    }

    public BusNotFoundException(String message) {
        super(message);
    }
}
