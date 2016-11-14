package exceptions;

public class CacheMissException extends RuntimeException{
	
	public CacheMissException(String message){
		super(message);
	}

}
