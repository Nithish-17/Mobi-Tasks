package exception;

public class InvalidEntityIdException extends RuntimeException{

    public InvalidEntityIdException(String message){
        super(message);
    }

}
