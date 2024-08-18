package exception;

public class AdminNotFoundException extends Exception{
    public AdminNotFoundException() {
    }

    public AdminNotFoundException(String message) {
        super(message);
    }
}
