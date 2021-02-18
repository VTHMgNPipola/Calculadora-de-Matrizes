package com.vthmgnpipola.matrixcalc.comandos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComandoRegistrado {
    String value();
    boolean exportavel() default true;
}
