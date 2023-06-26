package etu2032.framework.annotation;

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

/**
 * 
 *	The Scope annotation is used to annotate a class. 
 *	The class may be annoted with this annotation to be singleton.
 *	Note : if a class is not singleton it doesn't need this annotation.
 * 
 *	Possible Values:
 * 		- Singleton : declare the class as singleton
 * 		- Empty : declare the class as not singleton class 
 * 
 * @param      name { design the class type : Singleton or empty  }
 * 
 * @author     Manoary Sarobidy ETU 2032
 */

public @interface Scope{
	public final String SINGLETON = "Singleton";
	String name() default "";
}