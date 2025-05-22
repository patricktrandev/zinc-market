package cloud.tientn.zinc.exception;

public class ResourceAlreadyExistException extends RuntimeException{
    private String fieldName;

    public ResourceAlreadyExistException( String fieldName) {
        super("Resource already exists with "+fieldName);
        this.fieldName = fieldName;
    }
}
