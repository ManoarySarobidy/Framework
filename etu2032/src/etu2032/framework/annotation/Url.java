package etu2032.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

/**
 * The Url annotation is used to annotate
 * a function to an Url. That url will be the path that will be acceded by the controller to access the function
 * 
 * Example :
 * @Url( url = "/get" )
 * public void function(){
 *  // Some code inside here
 * }
 * 
 * @author Manoary Sarobidy
 */

public @interface Url {

    /**
     *  Will Maintain the url for what the web will be accessing it
     *  May be a method To will be effective
     * @return none
     */
    
    String url() default "";
    String method() default "GET";
    
}
