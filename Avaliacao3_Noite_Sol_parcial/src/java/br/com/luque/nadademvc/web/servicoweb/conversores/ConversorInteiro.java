package br.com.luque.nadademvc.web.servicoweb.conversores;

/**
 * Este conversor sabe como converter entre int e String.
 * @author Leandro Luque
 */
@Conversor
public class ConversorInteiro {
    
    public int deString(String valor) {
        return Integer.valueOf(valor);
    }
    
    public String paraString(int valor) {
        return Integer.toString(valor);
    }
    
}
