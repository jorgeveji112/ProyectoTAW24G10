<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: carlossgaalvez
  Date: 11/5/24
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<UsuarioEntity> entrenadoresBodyBuilding = (List<UsuarioEntity>) request.getAttribute("entrenadoresBodyBuilding"); %>
<% List<UsuarioEntity> entrenadoresCrossTraining = (List<UsuarioEntity>) request.getAttribute("entrenadoresCrossTraining"); %>

<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/listaEntrenadores.css">
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
    <h1 class="body">Entrenadores Body-Building:</h1>
    <h1 class="cross">Entrenadores Cross-Training:</h1>
    <div class="contenido">
        <table class="tabla">
            <tbody>
                <%if(entrenadoresBodyBuilding.size() > 0){
                    for(UsuarioEntity entrenador : entrenadoresBodyBuilding){ %>
                        <tr>
                            <td><%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></td>
                            <td>
                                <button class="boton" onclick="window.location='/adminMain/clientesEntrenador/<%= entrenador.getId()%>'">Clientes Asignados</button>
                            </td>
                            <td>
                                <button class="boton" onclick="window.location='/adminMain/borrarEntrenador/<%= entrenador.getId()%>'">Borrar</button>
                            </td>
                        </tr>
                    <%}}else{%>
                    <h3>No hay entrenadores Body Building disponibles</h3>
                <%}%>

            </tbody>
        </table>
    </div>

    <div class="contenido">
        <table class="tabla">
            <tbody>
            <%if(entrenadoresCrossTraining.size() > 0){
                for(UsuarioEntity entrenador : entrenadoresCrossTraining){ %>
                    <tr>
                        <td><%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></td>
                        <td>
                            <button class="boton" onclick="window.location='/adminMain/clientesEntrenador/<%= entrenador.getId()%>'">Clientes Asignados</button>
                        </td>
                        <td>
                            <button class="boton" onclick="window.location='/adminMain/borrarEntrenador/<%= entrenador.getId()%>'">Borrar</button>
                        </td>
                    </tr>
                <%}
            }else{%>
            <h3>No hay entrenadores Cross Training disponibles</h3>
            <%}%>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>