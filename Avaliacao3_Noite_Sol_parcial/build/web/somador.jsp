<!DOCTYPE html>
<html lang="pt">
    <head>
        <title>Exemplo de Requisi��o e Resposta</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>Exemplo de Requisi��o e Resposta</div>
        <form action="${pageContext.request.contextPath}/do/calculadora/somar" method="post">
            <label for="numero1">N�mero 1</label><input type="text" name="numero1" id="numero1"/>
            <label for="numero2">N�mero 2</label><input type="text" name="numero2" id="numero2"/>
            <input type="submit" name="somar" value=" Somar " />
        </form>
    </body>
</html>
