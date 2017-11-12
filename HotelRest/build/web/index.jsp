<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Consultar habitaciones libres:</h1><br>
        <form action="webresources/generic/consultaLibres" method="get">
            <input type="text" name="id"/>
            <input type="text" name="fecha"/>
            <input type="submit" name="submit" value="Send"/>
            <br>
        </form>
        <h1>Reservar una habitacion:</h1><br>
        <form action="webresources/generic/reservarHab" method="post">
            <input type="text" name="id"/>
            <input type="text" name="fecha"/>
            <input type="submit" name="submit" value="Send"/>
            <br>
        </form>

    </body>
</html>
