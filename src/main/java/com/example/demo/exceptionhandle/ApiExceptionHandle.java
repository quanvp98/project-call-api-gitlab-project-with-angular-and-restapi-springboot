package com.example.demo.exceptionhandle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.constant.ErrorMessage;

@RestControllerAdvice
public class ApiExceptionHandle {	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleAllException(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(ex.getLocalizedMessage(), ""));
	}
	
	@ExceptionHandler(ArgumentNullException.class)
	public ResponseEntity<ErrorMessage> handleArgumentNullException(ArgumentNullException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
				(new ErrorMessage(exception.getMessageError(), "Change Request"));
	}
	
	@ExceptionHandler(SaveErrorException.class)
	public ResponseEntity<ErrorMessage> handleSaveErrorException() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body
				(new ErrorMessage("Save fail", ""));
	}
	
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity<ErrorMessage> handleLoginFailException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				new ErrorMessage("UserName or Password not valid", "Enter again UserName and Password")
		);
	}
	
	@ExceptionHandler(ObjectNullException.class)
	public ResponseEntity<ErrorMessage> handleObjectNullException(ObjectNullException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				new ErrorMessage(exception.getMessageError(), "Change Request"));
	}
	
	@ExceptionHandler(UpdateErrorException.class)
	public ResponseEntity<ErrorMessage> handleUpdateErrorException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body
				(new ErrorMessage("Wrong User's Information", "Check again User's Information"));
	}

	@ExceptionHandler(ListEmptyException.class)
	public ResponseEntity<ErrorMessage> handleListEmptyException() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body
				(new ErrorMessage("Database haven't data", "Verify again Request"));
	}
	
	@ExceptionHandler(ObjectIsExistException.class)
	public ResponseEntity<ErrorMessage> handleObjectIsExistException(ObjectIsExistException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body
				(new ErrorMessage(exception.getMessageError(), exception.getTreatmentError()));
	}
}
