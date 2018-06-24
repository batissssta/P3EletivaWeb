package br.com.luque.nadademvc.web.servicoweb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Esta anotação deve ser utilizada em parâmetros de métodos @RecursoWeb. Ela
 * serve para definir o nome do parâmetro na requisição.
 *
 * @author Leandro Luque
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParametroRequisicao {

    public String nome() default "_____nenhum_____";

    public boolean obrigatorio() default false;

    public String padrao() default "_____nenhum_____";
    
}
