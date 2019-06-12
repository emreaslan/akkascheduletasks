package demo.datatypes;

public abstract class BaseCommand {
    private final String message;

    public BaseCommand(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
