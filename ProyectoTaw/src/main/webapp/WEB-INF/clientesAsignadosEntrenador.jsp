<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: carlossgaalvez
  Date: 5/6/24
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%
    List<UsuarioEntity> listaClientes = (List<UsuarioEntity>) request.getAttribute("listaClientes");
    UsuarioEntity entrenador = (UsuarioEntity) request.getAttribute("entrenador");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/clientesAsignadosEntrenador.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="logo"><img src="/img/logoGym.png"></div>
    <ul class="enlaces">
        <li><a href="/adminMain/inicio">Inicio</a></li>
        <li><a href="/adminMain/entrenadores" id="activo">Entrenadores</a></li>
        <li><a href="/adminMain/clientes">Clientes</a></li>
        <li><a href="/adminMain/solicitudes">Solicitudes</a></li>
        <li><a href="/adminMain/ejercicios">Ejercicios</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <h1 class="encabezado">Clientes asignados del entrenador: <%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></h1> </br>

    <div class="contenido">
        <%if(listaClientes.isEmpty()){%>
            <h2>El entrenador no tiene actualmente ningún cliente asignado</h2>
        <%} else {%>
        <div class="div-tabla">
        <table class="tabla">
            <tr>
                <th>DNI</th>
                <th>Nombre</th>
                <th>Genero</th>
                <th></th>
            </tr>
            <%for(UsuarioEntity u : listaClientes){%>
            <tr>
                <td><%=u.getDni()%></td>
                <td><%=u.getNombre()%> <%=u.getApellidos()%></td>
                <td><%=u.getGenero()%></td>
                <td><button class="boton" onclick="window.location='/adminMain/entrenador/desasignarCliente/<%=u.getId()%>'">Desasignar entrenador</button></td>
            </tr>
            <%}%>
        </table>
        </div>
        <%}%>
    </div>
    <div class="contenido2">
        <div class="filtro">
            <form class="formulario" action="/adminMain/filtrar/clientesAsignados" method="post">
                <p>Escribe nombre, apellidos o DNI</p>
                <input type="hidden" name="idEntrenador" value="<%=entrenador.getId()%>">
                <input class="entrada" name="filtro"> </br>
                <input class="boton" type="submit" value="Filtrar">
            </form>
        </div>
        <button class="boton" onclick="window.location='/adminMain/nuevosClientesEntrenador/<%= entrenador.getId()%>'">Asignar nuevos </br>clientes</button>
    </div>
</div>
</body>
</html>

