package etu2032.framework.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 
 * @author Manoary Sarobidy
 * 
 * This Rest annotation is used to 
 * annotate a method that the method return 
 * value must be converted to json
 * 
 */

public @interface Rest{
	
}