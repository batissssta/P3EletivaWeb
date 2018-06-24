package br.com.luque.nadademvc.web.produto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação define atributos que devem receber injeção.
 *
 * @author Leandro Luque
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Injetar {

}
