package br.com.luque.nadademvc.web.servicoweb;

import java.lang.reflect.Method;

/**
 * Esta classe implementa maepamentos de recursos, contendo o caminho relativo
 * do recurso, os métodos que o recurso pode responder, a classe do serviço web
 * e o método que implementa o recurso.
 *
 * @author Leandro Luque
 */
public class MapeamentoRecurso {

    private String caminho;
    private Class classe;
    private Method metodo;
    private MetodoHTTP[] metodosHTTP;

    public MapeamentoRecurso() {
    }

    public MapeamentoRecurso(String caminho, Class classe, Method metodo, MetodoHTTP[] metodosHTTP) {
        this.caminho = caminho;
        this.classe = classe;
        this.metodo = metodo;
        this.metodosHTTP = metodosHTTP;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public Method getMetodo() {
        return metodo;
    }

    public void setMetodo(Method metodo) {
        this.metodo = metodo;
    }

    public MetodoHTTP[] getMetodosHTTP() {
        return metodosHTTP;
    }

    public void setMetodosHTTP(MetodoHTTP[] metodosHTTP) {
        this.metodosHTTP = metodosHTTP;
    }

    @Override
    public String toString() {
        return "MapeamentoRecurso{" + "caminho=" + caminho + ", classe=" + classe + ", metodo=" + metodo + ", metodosHTTP=" + metodosHTTP + '}';
    }

}
