
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Consultar plazas libres:</h1><br>
        <form action="webresources/generic/consultaLibres" method="get">
            <input type="text" name="id"/>
            <input type="text" name="fecha"/>
            <input type="submit" name="submit" value="Send"/>
            <br>
        </form>
        <h1>Reservar una plaza:</h1><br>
        <form action="webresources/generic/reservarPlaza" method="post">
            <input type="text" name="id"/>
            <input type="text" name="fecha"/>
            <input type="submit" name="submit" value="Send"/>
            <br>
        </form>

    </body>
</html>
