package br.com.luque.nadademvc.web.produto;

/**
 * Esta classe implementa categorias de produtos.
 *
 * @author Leandro Luque
 */
public class Categoria {

    private String nome;
    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
