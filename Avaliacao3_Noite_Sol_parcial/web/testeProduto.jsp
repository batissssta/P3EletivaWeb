<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Teste de Preenchimento de Produto</title>
    </head>
    <body>
        <h1>Teste de Preenchimento de Produto</h1>
        <form action="${pageContext.request.contextPath}/do/produtos/testar">
            <p><label for="produto.nome">Nome:</label><input type="text" name="produto.nome" id="produto.nome" /></p>
            <p><label for="produto.descricao">Descrição:</label><input type="text" name="produto.descricao" id="produto.descricao" /></p>
            <p><label for="produto.categoria.nome">Nome da Categoria:</label><input type="text" name="produto.categoria.nome" id="produto.categoria.nome" /></p>
            <input type="submit" name="enviar" value =" Testar " />
        </form>
    </body>
</html>
