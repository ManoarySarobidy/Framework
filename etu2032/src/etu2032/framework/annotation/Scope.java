package etu2032.framework.annotation;

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface Scope{
	String name() default "";
}