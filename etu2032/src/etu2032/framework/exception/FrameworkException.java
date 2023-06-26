package etu2032.framework.exception;

public class FrameworkException extends Exception{
	public static final String baseMessage = "An errors appeared on the framework : ";
	public FrameworkException(){
		super( baseMessage );
	}

	public FrameworkException( String message ){
		super( FrameworkException.baseMessage + message );
	}
}