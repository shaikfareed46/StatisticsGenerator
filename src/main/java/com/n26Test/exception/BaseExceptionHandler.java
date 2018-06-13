/**
 * 
 */
package com.n26Test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Shaik Fareed
 *
 */
@ControllerAdvice
public class BaseExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	public enum APIError {
	    
	    VALIDATION_EMPTY_REQUEST_BODY(101, "Empty request body"),
	    UNEXPECTED_ERROR(102, "Internal API Error");

	    private Integer code;
	    private String message;

	    APIError(Integer code, String message){
	        this.code = code;
	        this.message = message;
	    }

	    public Integer code(){
	        return this.code;
	    }

	    public String message(){
	        return this.message;
	    }
	}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exptyRequestBody(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorResponse(APIError.VALIDATION_EMPTY_REQUEST_BODY.code(), APIError.UNEXPECTED_ERROR.message());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorResponse(APIError.UNEXPECTED_ERROR.code(), APIError.UNEXPECTED_ERROR.message());
    }
	
}
