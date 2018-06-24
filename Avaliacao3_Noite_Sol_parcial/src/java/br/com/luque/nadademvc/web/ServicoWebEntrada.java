package br.com.luque.nadademvc.web;

import br.com.luque.nadademvc.web.servicoweb.ServicoWeb;
import br.com.luque.nadademvc.web.servicoweb.RecursoWeb;
import br.com.luque.nadademvc.web.servicoweb.MapeamentoRecurso;
import br.com.luque.nadademvc.util.DescobridorClasses;
import br.com.luque.nadademvc.web.produto.Injetar;
import br.com.luque.nadademvc.web.produto.Injetavel;
import br.com.luque.nadademvc.web.servicoweb.MetodoHTTP;
import br.com.luque.nadademvc.web.servicoweb.ParametroRequisicao;
import br.com.luque.nadademvc.web.servicoweb.ResultadoRequisicao;
import br.com.luque.nadademvc.web.servicoweb.ResultadoRequisicao.TipoRetorno;
import br.com.luque.nadademvc.web.servicoweb.conversores.Conversor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Esta classe implementa um 'Front-Controller' que recebe parte das requisições
 * da aplicação.
 *
 * @author Leandro Luque
 */
public class ServicoWebEntrada extends HttpServlet {

    /**
     * Mapa entre recursos de URL e métodos de classes de serviço web.
     */
    private Map<String, MapeamentoRecurso> mapeamentoRecursos;

    /**
     * Mapa de conversores que sabem como converter de String para algum outro
     * tipo.
     */
    private Map<Class, Object> conversores;

    /**
     * Lista de injetaveis.
     */
    private List<Class> injetaveis;

    private final String NENHUM = "_____nenhum_____";

    /**
     * Este método é executada na primeira vez que a Controladora for carregada.
     *
     * @param config Configuração da Servlet.
     * @throws ServletException Caso ocorra algum erro referente à configuração.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.encontrarServicosWeb();
        this.encontrarConversores();
        this.encontrarInjetaveis();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contextoAplicacao = request.getContextPath();
        String caminhoServlet = contextoAplicacao + "/do";
        String caminhoRecurso = request.getRequestURI();
        caminhoRecurso = caminhoRecurso.substring(caminhoRecurso.indexOf(caminhoServlet) + caminhoServlet.length());

        // Verifica o caminho do recurso.
        System.out.println("LOG: Foi recebida uma requisição para: " + caminhoRecurso);
        MapeamentoRecurso mapeamentoRecurso;
        if (null != (mapeamentoRecurso = mapeamentoRecursos.get(caminhoRecurso))) {

            // Verifica se o tipo de método HTTP confere com o que o método pode
            // receber.
            boolean achouUmMetodoQueSeAplica = false;
            for (MetodoHTTP metodoHTTP : mapeamentoRecurso.getMetodosHTTP()) {
                if (metodoHTTP.name().equals(request.getMethod())) {
                    achouUmMetodoQueSeAplica = true;
                    break;
                }
            }
            if (achouUmMetodoQueSeAplica) {
                System.out.println("LOG: Será executado o método " + mapeamentoRecurso.getMetodo().getName() + " da classe " + mapeamentoRecurso.getClasse().getName());

                // Descobre os parâmetros que o método da classe espera receber.
                Parameter[] parametros = mapeamentoRecurso.getMetodo().getParameters();
                System.out.println("LOG: Quantos parâmetros ? " + parametros.length);
                Object[] valoresParametros = new Object[parametros.length];
                int i = 0;
                for (Parameter parametro : parametros) {
                    // Verifica se existe um parâmetro de requisição com o nome do 
                    // do parâmetro especificado na anotação ParametroRequisicao.
                    String nomeParametro = null;
                    ParametroRequisicao parametroRequisicao;
                    if (null != (parametroRequisicao = (ParametroRequisicao) parametro.getDeclaredAnnotation(ParametroRequisicao.class))) {
                        nomeParametro = parametroRequisicao.nome();
                    }

                    // Verifica se o parâmetro é obrigatório e foi informado.
                    if (parametroRequisicao.obrigatorio() && null == request.getParameter(nomeParametro) && NENHUM.equals(parametroRequisicao.padrao())) {
                        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);
                        return;
                    }

                    // Verifica se o parâmetro é uma String.
                    if (String.class == parametro.getType()) {
                        valoresParametros[i] = request.getParameter(nomeParametro);
                    } else {
                        // Verifica se existe um conversor para o tipo do parâmetro.
                        Object conversor;
                        if (null != (conversor = conversores.get(parametro.getType()))) {
                            try {
                                valoresParametros[i] = conversor.getClass().getDeclaredMethod("deString", String.class).invoke(conversor, request.getParameter(nomeParametro));
                            } catch (Exception ex) {
                                //ex.printStackTrace();
                            }
                        } else {
                            // Caso o objeto não tenha um conversor, verifica se ele pode ser preenchido com 
                            // os parâmetros da requisição.
                            Object objeto = null;
                            try {
                                System.out.println("Vou tentar preenche um objeto do tipo " + parametro.getType().getName());
                                objeto = parametro.getType().newInstance();
                                valoresParametros[i] = objeto;
                                System.out.println("    Instância criada...");
                                preencher(objeto, request, nomeParametro);
                            } catch (Exception ex) {
                                //ex.printStackTrace();
                            }
                        }
                    } // Fim if tipo parâmetro.
                    i++;
                } // Fim para cada parâmetro.

                // Executa o método e recupera o ResultadoRequisicao.
                Object objeto;
                try {
                    objeto = mapeamentoRecurso.getClasse().newInstance();

                    // #####
                    // ##### Injeta os atributos disponíveis na classe.
                    for (Field atributo : mapeamentoRecurso.getClasse().getDeclaredFields()) {
                        if (null != atributo.getDeclaredAnnotation(Injetar.class)) {
                            if (this.injetaveis.contains(atributo.getClass())) {
                                atributo.setAccessible(true);
                                atributo.set(objeto, this.injetaveis.get(this.injetaveis.indexOf(atributo.getClass())).newInstance());
                            } else {
                                System.out.println("AVISO: Não foi possível encontrar classe para injetar no atributo.");
                            }
                        }
                    }

                    ResultadoRequisicao resultadoRequisicao = (ResultadoRequisicao) mapeamentoRecurso.getMetodo().invoke(objeto, valoresParametros);

                    if (TipoRetorno.MESMA_REQUISICAO == resultadoRequisicao.getTipoRetorno()) {

                        for (Entry<String, Object> dado : resultadoRequisicao.getDados().entrySet()) {
                            request.setAttribute(dado.getKey(), dado.getValue());
                        }

                        request.getRequestDispatcher(resultadoRequisicao.getCaminhoRelativoRecursoRetorno()).forward(request, response);

                    } else {
                        if (!resultadoRequisicao.getDados().isEmpty()) {
                            System.out.println("AVISO: A resposta tem dados, mas o tipo de retorno é NOVA_REQUISIÇÃO. Os dados não serão enviados.");
                        }

                        response.sendRedirect(response.encodeRedirectURL(resultadoRequisicao.getCaminhoRelativoRecursoRetorno()));
                    }

                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException erro) {
                    System.out.println("ERRO: " + erro.getMessage());
                    erro.printStackTrace();
                }

            } else { // Fim se algum método HTTP se aplica.
                System.out.println("LOG: O tipo de requisição recebida não corresponde aos tipos que o " + mapeamentoRecurso.getMetodo().getName() + " da classe " + mapeamentoRecurso.getClasse().getName() + " aceita");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            // Retorna um erro 404 para o usuário.
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void preencher(Object objeto, HttpServletRequest request, String nomeParametro) {
        System.out.println("    Preenchendo com nome base " + nomeParametro);
        Field[] atributos = objeto.getClass().getDeclaredFields();
        for (Field atributo : atributos) {
            atributo.setAccessible(true);
            // Verifica se o atributo é uma String.
            if (String.class == atributo.getType()) {
                try {
                    atributo.set(objeto, request.getParameter(nomeParametro + "." + atributo.getName()));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            } else {
                // Verifica se existe um conversor para o tipo do atributo.
                Object conversor;
                if (null != (conversor = conversores.get(atributo.getType()))) {
                    try {
                        atributo.set(objeto, conversor.getClass().getDeclaredMethod("deString", String.class).invoke(conversor, request.getParameter(nomeParametro + "." + atributo.getName())));
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                } else {
                    // Caso o atributo não tenha um conversor, tenta preenchê-lo recursivamente.
                    Object objetoAtributo;
                    try {
                        System.out.println("Vou tentar preenche um objeto do tipo " + atributo.getType().getName());
                        objetoAtributo = atributo.getType().newInstance();
                        atributo.set(objeto, objetoAtributo);
                        System.out.println("    Instância criada...");
                        preencher(objetoAtributo, request, nomeParametro + "." + atributo.getName());
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }
                }
            } // Fim if tipo parâmetro.
            atributo.setAccessible(false);
        }
    }

    private void encontrarServicosWeb() throws ServletException {
        try {
            // Procura no pacote atual e em subpacotes por classes anotadas
            // com @ServicoWeb. Estas classes implementam serviços para a web.
            Class[] classes = DescobridorClasses.getClasses(getClass().getPackage().getName());

            mapeamentoRecursos = new HashMap<>();
            for (Class classe : classes) {
                ServicoWeb servicoWeb;
                if (null != (servicoWeb = (ServicoWeb) classe.getDeclaredAnnotation(ServicoWeb.class))) {

                    // Monta o caminho relativo do recurso base.
                    String caminhoRelativoBase = servicoWeb.caminhoBase();
                    if (!caminhoRelativoBase.endsWith("/")) {
                        caminhoRelativoBase += "/";
                    }

                    // #####
                    // ##### Vale: 2,5 pontos.
                    // ##### Ajustar o código seguinte para que ele procure por métodos anotados com
                    // ##### @RecursoWeb (em classes @ServicoWeb) mesmo que herdados de superclasses
                    // ##### que não sejam anotadas com #ServicoWeb.
                    // #####
                    //
                    // Procura por métodos anotados com @RecursoWeb.
                    Class classeAtual = classe;
                    while (classeAtual != null) {
                        Method[] methods = classeAtual.getDeclaredMethods();
                        for (Method metodo : methods) {

                            RecursoWeb recursoWeb;
                            if (null != (recursoWeb = (RecursoWeb) metodo.getDeclaredAnnotation(RecursoWeb.class))) {

                                if (ResultadoRequisicao.class == metodo.getReturnType()) {

                                    String caminhoRelativoRecurso = caminhoRelativoBase;

                                    if (recursoWeb.recurso().startsWith("/")) {
                                        // Descarta a "/" inicial.
                                        caminhoRelativoRecurso += recursoWeb.recurso().substring(1);
                                    } else {
                                        caminhoRelativoRecurso += recursoWeb.recurso();
                                    }

                                    if (caminhoRelativoRecurso.endsWith("/")) {
                                        caminhoRelativoRecurso = caminhoRelativoRecurso.substring(0, caminhoRelativoRecurso.length() - 1);
                                    }

                                    MapeamentoRecurso mapeamentoRecurso = new MapeamentoRecurso(caminhoRelativoRecurso, classe, metodo, recursoWeb.metodo());
                                    mapeamentoRecursos.put(caminhoRelativoRecurso, mapeamentoRecurso);

                                } else {
                                    System.out.println("AVISO: O método " + metodo.getName() + " da classe " + classe.getName() + " está anotado como @RecursoWeb, mas não retorna ResultadoRequisicao. Por isso, ele foi desconsiderado.");
                                }

                            } // Fim se o método for anotado com RecursoWeb.
                        } // Fim para todo método.
                        classeAtual = classeAtual.getSuperclass();
                    } // Fim da repetição para buscar superclasses.
                } // Fim se a classe for anotada com ServicoWeb.
            } // Fim para cada classe.

            // Imprime os mapeamentos existentes (apenas para registro).
            System.out.println("==============================================================================");
            System.out.println("LOG: MAPEAMENTOS DE RECURSOS");
            for (MapeamentoRecurso mapeamentoRecurso : mapeamentoRecursos.values()) {
                System.out.println(mapeamentoRecurso);
            }
            System.out.println("==============================================================================");

        } catch (IOException | ClassNotFoundException erro) {
            throw new ServletException("Erro ao carregar classe do pacote de serviço web ou de algum de seus subpacotes.");
        }
    }

    private void encontrarConversores() throws ServletException {
        try {
            // Procura no pacote atual e em subpacotes por classes anotadas
            // com @Conversor. Estas classes implementam conversores.
            Class[] classes = DescobridorClasses.getClasses(getClass().getPackage().getName());

            conversores = new HashMap<>();
            for (Class classe : classes) {
                System.out.println("LOG: Analisando classe de conversor " + classe.getName());
                Conversor conversor;
                if (null != (conversor = (Conversor) classe.getDeclaredAnnotation(Conversor.class))) {

                    // Verifica se o conversor tem os métodos necessários.
                    Method[] metodos = classe.getDeclaredMethods();
                    boolean temParaString = false;
                    boolean temDeString = false;
                    for (Method metodo : metodos) {
                        System.out.println("LOG: Analisando método " + metodo.getName() + " do conversor " + classe.getName());
                        if (metodo.getName().equals("deString")) {
                            if (metodo.getParameterTypes().length == 1 && String.class == metodo.getParameterTypes()[0] && Void.class != metodo.getReturnType()) {
                                temDeString = true;
                            } else {
                                System.out.println("WARNING: O método deString do conversor deve receber apenas uma String como parâmetro e deve retornar algum valor. Como não é o caso, ele foi desconsiderado.");
                            }
                        } else if (metodo.getName().equals("paraString")) {
                            if (metodo.getParameterTypes().length == 1 && String.class == metodo.getReturnType()) {
                                temParaString = true;
                            } else {
                                System.out.println("WARNING: O método paraString do conversor deve receber um objeto e retornar uma String. Como não é o caso, ele foi desconsiderado.");
                            }
                        }
                    }

                    if (temDeString && temParaString) {
                        try {
                            this.conversores.put(classe.getDeclaredMethod("deString", String.class).getReturnType(), classe.newInstance());
                            System.out.println("LOG: Adicionado conversor da classe " + classe.getName());
                        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("WARNING: A classe de conversor " + classe.getName() + " deve ter um método deString e paraString com as assinaturas válidas. Como não é o caso, ele foi desconsiderado.");
                    }

                } // Fim se a classe for anotada com ServicoWeb.
            } // Fim para cada classe.

            // Imprime os mapeamentos existentes (apenas para registro).
            System.out.println("==============================================================================");
            System.out.println("LOG: MAPEAMENTOS DE CONVERSORES");
            for (Class classe : conversores.keySet()) {
                System.out.println(classe.getSimpleName());
            }
            System.out.println("==============================================================================");

        } catch (IOException | ClassNotFoundException erro) {
            throw new ServletException("Erro ao carregar classe do pacote de serviço web ou de algum de seus subpacotes.");
        }
    }

    private void encontrarInjetaveis() throws ServletException {
        try {
            // Procura no pacote atual e em subpacotes por classes anotadas
            // com @Inejtavel. Estas classes implementam objetos que podem
            // ser injetados pela controladora.
            Class[] classes = DescobridorClasses.getClasses(getClass().getPackage().getName());

            injetaveis = new ArrayList<>();
            for (Class classe : classes) {
                Injetavel injetavel;
                if (null != (injetavel = (Injetavel) classe.getDeclaredAnnotation(Injetavel.class))) {

                    this.injetaveis.add(classe);

                } // Fim se a classe for anotada com Injetavel.
            } // Fim para cada classe.

            // Imprime os mapeamentos existentes (apenas para registro).
            System.out.println("==============================================================================");
            System.out.println("LOG: MAPEAMENTOS DE INJETÁVEIS");
            for (Class classe : injetaveis) {
                System.out.println(classe.getSimpleName());
            }
            System.out.println("==============================================================================");

        } catch (IOException | ClassNotFoundException erro) {
            throw new ServletException("Erro ao carregar classe do pacote de serviço web ou de algum de seus subpacotes.");
        }
    }

}
