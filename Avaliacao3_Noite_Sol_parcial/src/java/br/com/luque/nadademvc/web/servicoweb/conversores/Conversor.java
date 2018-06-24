package br.com.luque.nadademvc.web.servicoweb.conversores;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação define conversores de String para qualquer outro tipo e
 * vice-versa.
 * @author Leandro Luque
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Conversor {
    
}
