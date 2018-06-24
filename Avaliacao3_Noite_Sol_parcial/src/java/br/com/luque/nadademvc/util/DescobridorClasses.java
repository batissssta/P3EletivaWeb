package br.com.luque.nadademvc.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Esta classe utilitária implementa métodos que procuram classes existentes em
 * um pacote e nos seus subpacotes. Não é possível fazer isso unicamente com
 * reflection.
 *
 * @author Leandro Luque
 */
public class DescobridorClasses {

    /**
     * Procura por todas as classes que estejam disponíveis no pacote
     * especificado e em seus subpacotes.
     *
     * @param nomePacote O nome do pacote.
     * @return Uma lista das classes encontradas.
     * @throws ClassNotFoundException Caso haja um problema ao tentar recuperar
     * uma classe a partir do seu nome.
     * @throws IOException Caso ocorra algum erro ao tentar acessar um arquivo
     * ou pasta.
     */
    public static Class[] getClasses(String nomePacote)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String caminhoPasta = nomePacote.replace('.', '/');
        Enumeration<URL> recursos = classLoader.getResources(caminhoPasta);
        List<File> pastas = new ArrayList<>();
        while (recursos.hasMoreElements()) {
            URL recurso = recursos.nextElement();
            pastas.add(new File(recurso.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File pasta : pastas) {
            classes.addAll(procurarClasses(pasta, nomePacote));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Método recursivo que procurar classes em uma pasta e nas suas subpastas.
     *
     * @param pasta A pasta raiz a partir da qual será feita a busca.
     * @param nomePacote O nome do pacote referente a pasta raiz.
     * @return As classes.
     * @throws ClassNotFoundException Caso haja um problema ao tentar recuperar
     * uma classe a partir do seu nome.
     */
    public static List<Class> procurarClasses(File pasta, String nomePacote) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!pasta.exists()) {
            return classes;
        }
        File[] arquivos = pasta.listFiles();
        for (File arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                classes.addAll(procurarClasses(arquivo, nomePacote + "." + arquivo.getName()));
            } else if (arquivo.getName().endsWith(".class")) {
                classes.add(Class.forName(nomePacote + '.' + arquivo.getName().substring(0, arquivo.getName().length() - 6)));
            }
        }
        return classes;
    }

}
