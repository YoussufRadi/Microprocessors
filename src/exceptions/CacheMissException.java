package exceptions;

public class CacheMissException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CacheMissException(String message){
		super(message);
	}

}
