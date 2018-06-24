package br.com.luque.nadademvc.web.calculadora;

import br.com.luque.nadademvc.web.servicoweb.MetodoHTTP;
import br.com.luque.nadademvc.web.servicoweb.ParametroRequisicao;
import br.com.luque.nadademvc.web.servicoweb.RecursoWeb;
import br.com.luque.nadademvc.web.servicoweb.ResultadoRequisicao;
import br.com.luque.nadademvc.web.servicoweb.ServicoWeb;

/**
 * Esta classe implementa parcialmente um serviço web de calculadora.
 *
 * @author Leandro Luque
 */
@ServicoWeb(caminhoBase = "/calculadora")
public class CalculadoraServico {

    @RecursoWeb(recurso = "/somar", metodo = MetodoHTTP.POST)
    public ResultadoRequisicao somar(@ParametroRequisicao(nome = "numero1", obrigatorio = true) int numero1, @ParametroRequisicao(nome = "numero2", padrao = "10") int numero2) {
        ResultadoRequisicao resultadoRequisicao = new ResultadoRequisicao();
        resultadoRequisicao.addDado("soma", numero1 + numero2);
        resultadoRequisicao.setCaminhoRelativoRecursoRetorno("/resultadoSoma.jsp");
        return resultadoRequisicao;
    }

}
