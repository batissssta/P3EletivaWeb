package br.com.luque.nadademvc.web.servicoweb;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta classe armazena o resultado de uma requisição.
 *
 * @author Leandro Luque
 */
public class ResultadoRequisicao {

    public enum TipoRetorno {
        NOVA_REQUISICAO, MESMA_REQUISICAO
    };
    private TipoRetorno tipoRetorno = TipoRetorno.MESMA_REQUISICAO;

    private String caminhoRelativoRecursoRetorno;
    private Map<String, Object> dados = new HashMap<>();

    public TipoRetorno getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(TipoRetorno tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public String getCaminhoRelativoRecursoRetorno() {
        return caminhoRelativoRecursoRetorno;
    }

    public void setCaminhoRelativoRecursoRetorno(String caminhoRelativoRecursoRetorno) {
        this.caminhoRelativoRecursoRetorno = caminhoRelativoRecursoRetorno;
    }

    public Map<String, Object> getDados() {
        return dados;
    }

    public void addDado(String nome, Object valor) {
        this.dados.put(nome, valor);
    }

    public void setDados(Map<String, Object> dados) {
        this.dados = dados;
    }

    public Object getDado(String nome) {
        return this.dados.get(nome);
    }

}
