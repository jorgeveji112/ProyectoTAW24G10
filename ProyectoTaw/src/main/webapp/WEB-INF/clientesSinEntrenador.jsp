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

<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/clientesSinEntrenador.css">
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
    <h1 class="encabezado">Nuevos posibles clientes para el entrenador: <%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></h1> </br>

    <div class="contenido">
        <%if(listaClientes.size() == 0){%>
            <h3>No hay clientes para ser asignados</h3>
        <%} else{%>
        <table>
            <%for(UsuarioEntity u : listaClientes){%>
            <tr>
                <th><%=u.getDni()%></th>
                <th><%=u.getNombre()%> <%=u.getApellidos()%></th>
                <th><%=u.getGenero()%></th>
                <th><button class="boton" onclick="window.location='/adminMain/asignarClienteEntrenador?idCliente=<%=u.getId()%>&idEntrenador=<%=entrenador.getId()%>'">Asignar</button></th>
            </tr>
            <%}%>
        </table>
        <%}%>
    </div>
    <div class="contenido2">
        <div class="filtro">
            <form class="formulario" action="/adminMain/filtrar/clientesSinAsignar" method="post">
                <p>Escribe nombre, apellidos o DNI</p>
                <input type="hidden" name="idEntrenador" value="<%=entrenador.getId()%>">
                <input class="entrada" name="filtro">
                <input class="filtrar" type="submit" value="filtrar">
            </form>
        </div>
    </div>
</div>
</body>
</html>