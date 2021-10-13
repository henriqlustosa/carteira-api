package br.com.alura.carteira.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
	        super(message);
	    }

}
