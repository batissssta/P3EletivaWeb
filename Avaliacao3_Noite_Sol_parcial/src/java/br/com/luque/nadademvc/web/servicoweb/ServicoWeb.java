package br.com.luque.nadademvc.web.servicoweb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação deve ser utilizada em classes que implementam serviços para a
 * web.
 *
 * @author Leandro Luque
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServicoWeb {

    public String caminhoBase() default "/";

}
