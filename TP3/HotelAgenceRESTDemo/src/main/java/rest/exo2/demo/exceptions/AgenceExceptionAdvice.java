package rest.exo2.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AgenceExceptionAdvice {

	@ExceptionHandler(AgenceException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String agenceExceptionHandler(AgenceException e) {
		return String.format("{\"%s\": %s}", "error", e.getMessage());
	}
}
