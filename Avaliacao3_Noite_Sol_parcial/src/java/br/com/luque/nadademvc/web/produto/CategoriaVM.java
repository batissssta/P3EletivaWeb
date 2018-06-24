package br.com.luque.nadademvc.web.produto;

import java.util.Date;

/**
 * Esta classe implementa um modelo de visão de categorias de produtos.
 *
 * @author Leandro Luque
 */
public class CategoriaVM {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "CategoriaVM{" + "nome=" + nome + '}';
    }

}
