package br.com.luque.nadademvc.web.produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta classe implementa um DAO de produtos que simula a existência de um banco
 * de dados.
 *
 * @author Leandro Luque
 */
public class ProdutoDAO {

    private final Map<Long, Produto> idsParaProdutos = new HashMap<>();

    public void inserir(Produto produto) {
        this.idsParaProdutos.put(produto.getId(), produto);
    }

    public void alterar(Produto produto) {
        this.idsParaProdutos.put(produto.getId(), produto);
    }

    public void excluir(Long id) {
        this.idsParaProdutos.remove(id);
    }

    public Produto consultarPorId(Long id) {
        return idsParaProdutos.get(id);
    }

    public List<Produto> consultarTodos() {
        return new ArrayList<>(this.idsParaProdutos.values());
    }

}
