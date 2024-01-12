package spring.restful.login.v1.exception;

import org.springframework.http.HttpStatus;

public class CustomAPIException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;

    public CustomAPIException(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomAPIException(String message, HttpStatus httpStatus, String message1){
        super(message);
        this.httpStatus=httpStatus;
        this.message=message1;
    }

    public HttpStatus getHttpStatus(){
        return  httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
