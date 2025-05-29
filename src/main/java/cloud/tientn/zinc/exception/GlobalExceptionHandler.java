package cloud.tientn.zinc.exception;

import cloud.tientn.zinc.response.Response;
import cloud.tientn.zinc.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Response handleResourceNotFoundException(ResourceNotFoundException ex){
        return new Response(false, StatusCode.NOT_FOUND, ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Response handleInternalServerException(Exception ex){
        return new Response(false, StatusCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Response handleResourceAlreadyExistException(ResourceAlreadyExistException ex){
        return new Response(false, StatusCode.INVALID_ARGUMENT, ex.getMessage());
    }
    @ExceptionHandler(CustomBlobStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Response handleCustomBlobException(ResourceAlreadyExistException ex){
        return new Response(false, StatusCode.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Response handleAuthException(Exception ex){
        return new Response(false, StatusCode.UNAUTHORIZED, "Username or password is incorrect");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Response handleValidationException(MethodArgumentNotValidException ex){
        List<ObjectError> errorList= ex.getBindingResult().getAllErrors();
        Map<String, String> map= new HashMap<>(errorList.size());
        errorList.forEach(error -> {
            String key= ((FieldError) error).getField();
            String val= error.getDefaultMessage();
            map.put(key, val);
        });
        return new Response(false, StatusCode.INVALID_ARGUMENT, "Provided arguments are invalid.",map);
    }
}
