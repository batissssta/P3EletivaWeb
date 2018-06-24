package br.com.luque.nadademvc.web.produto;

import java.util.Date;

/**
 * Esta classe implementa um modelo de visão de produtos.
 *
 * @author Leandro Luque
 */
public class ProdutoVM {

    private Long id;
    private String nome;
    private String descricao;
    private Date dataLancamento;
    private double precoReferencia;
    private boolean ePerecivel;
    private CategoriaVM categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public double getPrecoReferencia() {
        return precoReferencia;
    }

    public void setPrecoReferencia(double precoReferencia) {
        this.precoReferencia = precoReferencia;
    }

    public boolean isePerecivel() {
        return ePerecivel;
    }

    public void setePerecivel(boolean ePerecivel) {
        this.ePerecivel = ePerecivel;
    }

    public CategoriaVM getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaVM categoria) {
        this.categoria = categoria;
    }

    public Produto preencher() {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setDataLancamento(dataLancamento);
        produto.setPrecoReferencia(0);
        produto.setePerecivel(ePerecivel);
        // Categoria?
        return produto;
    }

    @Override
    public String toString() {
        return "ProdutoVM{" + "id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", dataLancamento=" + dataLancamento + ", precoReferencia=" + precoReferencia + ", ePerecivel=" + ePerecivel + ", categoria=" + categoria + '}';
    }

}
