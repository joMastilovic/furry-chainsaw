package recipes.exception;

public class JsonRepositoryException extends RuntimeException {

    public JsonRepositoryException(String resource, Throwable cause) {
        super("There were errors accessing or processing data stored in " + resource, cause);
    }

}
