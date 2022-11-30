package rest.exo2.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ChambreExceptionAdvice {

	@ExceptionHandler(ChambreException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String agenceExceptionHandler(ChambreException e) {
		return String.format("{\"%s\": %s}", "error", e.getMessage());
	}
}
