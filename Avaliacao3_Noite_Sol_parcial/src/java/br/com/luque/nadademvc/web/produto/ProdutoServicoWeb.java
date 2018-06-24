package br.com.luque.nadademvc.web.produto;

import br.com.luque.nadademvc.web.servicoweb.MetodoHTTP;
import br.com.luque.nadademvc.web.servicoweb.ParametroRequisicao;
import br.com.luque.nadademvc.web.servicoweb.RecursoWeb;
import br.com.luque.nadademvc.web.servicoweb.ResultadoRequisicao;
import br.com.luque.nadademvc.web.servicoweb.ServicoWeb;

/**
 * Esta classe implementa um serviço web de produtos.
 *
 * @author Leandro Luque
 */
@ServicoWeb(caminhoBase = "/produtos")
public class ProdutoServicoWeb {

// #####
    // ##### Vale: 3,0 pontos.
    // ##### Escrever código para que este objeto não precise ser instanciado
    // ##### e que seja inserido automaticamente pela Controladora quando um
    // ##### objeto desta classe for criado.
    // ##### Dica: você deverá criar duas novas anotações @Injetavel e @Injetar.
    // ##### A anotação @Injetavel deverá ser inserida em toda classe de serviço,
    // ##### como ProdutoServico. A anotação @Injetar deve ser definida para
    // ##### o atributo de serviço desta classe. Ainda, você precisará fazer
    // ##### duas alterações na classe Controladora. A primeira delas envolverá
    // ##### varrer os pacotes procurando por classes que tenham a anotação
    // ##### @Injetavel. A segunda envolverá atribuir o valor deste atributo
    // ##### quando um objeto de classe de serviço web for instanciado.
    // #####    
    //private ProdutoServico produtoServico = new ProdutoServico();
    @Injetar
    private ProdutoServico produtoServico = new ProdutoServico();

    @RecursoWeb(recurso = "/editar", metodo = MetodoHTTP.GET)
    public ResultadoRequisicao consultarProduto(@ParametroRequisicao(nome = "id") long id) {
        // #####
        // ##### Vale: 1,5 pontos
        // ##### Escrever código para consultar um produto pelo id e retornar um
        // ##### objeto de ResultadoRequisicao contendo o produto a retornar.
        // #####
        throw new UnsupportedOperationException("Você deve retirar isso após implementar");
    }

    @RecursoWeb(metodo = MetodoHTTP.GET)
    public ResultadoRequisicao consultarProdutos() {
        // #####
        // ##### Vale: 1,5 pontos
        // ##### Escrever código para consultar todos os produtos por meio da
        // ##### camada de serviço e retornar um objeto de ResultadoRequisicao
        // ##### contendo os produtos retornados.
        // #####
        throw new UnsupportedOperationException("Você deve retirar isso após implementar");
    }

    @RecursoWeb(metodo = MetodoHTTP.POST)
    public ResultadoRequisicao inserir(@ParametroRequisicao(nome = "produto") ProdutoVM produto) {
        // #####
        // ##### Vale: 1,5 pontos
        // ##### Escrever código para inserir no banco de dados o produto recebido
        // ##### como parâmetro.
        // #####
        throw new UnsupportedOperationException("Você deve retirar isso após implementar");
    }

    @RecursoWeb(recurso = "/testar", metodo = MetodoHTTP.GET)
    public ResultadoRequisicao testar(@ParametroRequisicao(nome = "produto") ProdutoVM produto) {
        ResultadoRequisicao resultadoRequisicao = new ResultadoRequisicao();
        resultadoRequisicao.setCaminhoRelativoRecursoRetorno("/exibirResultadoTeste.jsp");
        resultadoRequisicao.addDado("produto", produto);
        return resultadoRequisicao;
    }
}
