package exception;

public class CancellationException extends Exception{
    public CancellationException() {
    }

    public CancellationException(String message) {
        super(message);
    }
}
