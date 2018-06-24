package br.com.luque.nadademvc.web.servicoweb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação deve ser utilizada em métodos de classes @ServicoWeb. Estes
 * métodos mapeiam recursos de URL para uma operação na aplicação.
 *
 * @author Leandro Luque
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecursoWeb {

    public String recurso() default "";

    public MetodoHTTP[] metodo() default {MetodoHTTP.QUALQUER};

}
