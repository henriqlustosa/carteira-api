package br.com.alura.carteira.infra;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.carteira.exceptions.ErrorResponse;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class TratamentosDeErros {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		return buildValidationErrorResponse(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
	}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

	private ResponseEntity<ErrorResponse> buildValidationErrorResponse(MethodArgumentNotValidException ex,
			HttpStatus status, String path) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.status(status.value())
				.erro(ex.getClass().getSimpleName())
				.message("Erro de Validacao").caminho(path).build();

		ex.getFieldErrors()
				.forEach(error -> errorResponse.adicionaErrosDeValidacao(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(status).body(errorResponse);
	}
	
	
	  @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
	        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro desconhecido",
	                request.getRequestURI());
	    }
	  
	    @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
	            HttpServletRequest request) {
	        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
	    }
	  
    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String message,
            String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
        		.status(status.value()).erro(ex.getClass().getSimpleName())
                .message(message)
                .caminho(path)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}