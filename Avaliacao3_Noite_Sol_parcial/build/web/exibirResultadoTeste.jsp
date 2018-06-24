<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado do Teste</title>
    </head>
    <body>
        <h1>Resultado do Teste</h1>
        <%= request.getAttribute("produto")%>
    </body>
</html>
