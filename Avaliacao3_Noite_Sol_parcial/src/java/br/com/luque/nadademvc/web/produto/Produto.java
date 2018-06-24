package br.com.luque.nadademvc.web.produto;

import java.util.Date;

/**
 * Esta classe implementa um modelo de visão de produtos.
 *
 * @author Leandro Luque
 */
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private Date dataLancamento;
    private double precoReferencia;
    private boolean ePerecivel;
    private Categoria categoria;

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
