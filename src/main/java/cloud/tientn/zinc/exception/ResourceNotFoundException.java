package cloud.tientn.zinc.exception;

public class ResourceNotFoundException extends RuntimeException{
    private Long id;
    private String username;

    public ResourceNotFoundException(String message, Long id) {
        super(message + " was not found with "+id);
        this.id = id;
    }
    public ResourceNotFoundException(String message, String username) {
        super(message + " was not found with "+username);
        this.id = id;
    }

}
