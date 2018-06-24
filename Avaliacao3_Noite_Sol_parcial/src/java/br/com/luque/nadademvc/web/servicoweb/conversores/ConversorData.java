package br.com.luque.nadademvc.web.servicoweb.conversores;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Este conversor sabe como converter entre Date e String.
 *
 * @author Leandro Luque
 */
@Conversor
public class ConversorData {

    private DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    public Date deString(String valor) {
        try {
            return formatador.parse(valor);
        } catch (ParseException ex) {
            System.out.println("ERROR: Não foi possível convertar de String para data considerando o formato dd/MM/yyyy.");
            return null;
        }
    }

    public String paraString(Date valor) {
        return formatador.format(valor);
    }

}
