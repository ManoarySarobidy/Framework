package etu2032.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)

/**
 * @author Manoary Sarobidy
 * 
 * The RequestParameter annotation is used to
 * declare a parameter inside a method
 * The name inside the annotation will be used to retrieve the
 * data
 * 
 */

public @interface RequestParameter{
	String name() default "";
}