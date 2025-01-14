package order.tracking.controller.error;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

	private Map<String,String> errorOutput = new HashMap<>();
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String,String> handleNoSuchElementException(NoSuchElementException ex){
		String message = "message";
		String statusReason = ex.toString();
		errorOutput.put(message, statusReason);
		return errorOutput;
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public Map<String,String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
		String message = "message";
		String statusReason = ex.toString();
		errorOutput.put(message,statusReason);
		return errorOutput;  
		
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public Map<String,String> handleIllegalArgumentException(IllegalArgumentException ex) {
		String message = "message";
		String statusReason = ex.toString();
		errorOutput.put(message,statusReason);
		return errorOutput;  
		
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public Map<String,String> handleDuplicateKeyException(DuplicateKeyException ex) {
		String message = "message";
		String statusReason = ex.toString();
		errorOutput.put(message,statusReason);
		return errorOutput;  
		
	}
}
