package etu2032.framework.exception;

public class UrlNotFoundException extends FrameworkException{
	public UrlNotFoundException( String url ){
		super( "The url : " + url + " doesn't exist on the project. Please verify your url" );
	}
}