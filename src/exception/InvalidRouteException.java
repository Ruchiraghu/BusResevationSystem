package exception;

public class InvalidRouteException extends Exception{
    public InvalidRouteException() {}

    public InvalidRouteException(String message) {
        super(message);
    }
}
