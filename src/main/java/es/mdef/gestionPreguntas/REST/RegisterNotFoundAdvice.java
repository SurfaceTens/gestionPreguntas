package es.mdef.gestionPreguntas.REST;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import es.mdef.gestionPreguntas.validation.RegisterNotFoundException;

@ControllerAdvice
public class RegisterNotFoundAdvice {
	@ResponseBody
	@ExceptionHandler(RegisterNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String empleadoNotFoundHandler(RegisterNotFoundException ex) {
		return ex.getMessage();
	}
}
