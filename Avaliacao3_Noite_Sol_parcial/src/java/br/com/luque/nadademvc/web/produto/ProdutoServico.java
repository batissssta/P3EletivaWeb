package br.com.luque.nadademvc.web.produto;

/**
 * Esta classe implementa um serviço de produtos.
 *
 * @author Leandro Luque
 */
public class ProdutoServico {

    public Produto consultarPorId(long id) {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = produtoDAO.consultarPorId(id);
        return produto;
    }

}
