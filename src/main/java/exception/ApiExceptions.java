package exception;

public class ApiExceptions extends RuntimeException{


    private final int statusCode;
    public ApiExceptions(int statusCode,String message){
        super(message);
        this.statusCode=statusCode;

    }
    public int getStatusCode() {
        return statusCode;
    }

}
