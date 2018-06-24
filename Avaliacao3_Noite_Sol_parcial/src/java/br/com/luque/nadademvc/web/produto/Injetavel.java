package br.com.luque.nadademvc.web.produto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação define injetáveis.
 *
 * @author Leandro Luque
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injetavel {

}
