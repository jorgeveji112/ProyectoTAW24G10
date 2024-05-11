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
        <li><a href="inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <h1>Lista de Entrenadores</h1>
    <div class="contenido">
        <div class="bodyBuilding" onclick="window.location='/'">
            <%if(entrenadoresBodyBuilding.size() > 0){
                for(UsuarioEntity entrenador : entrenadoresBodyBuilding){ %>
                    <h2><%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></h2>
                    <br>
                <%}
            }%>
        </div>
        <div class="CrossTraining" onclick="window.location='/'">
            <%if(entrenadoresCrossTraining.size() > 0){
                    for(UsuarioEntity entrenador : entrenadoresCrossTraining){ %>
                        <h2><%=entrenador.getNombre()%> <%=entrenador.getApellidos()%></h2>
                        <br>
                <%}
            }%>
        </div>
    </div>
</div>
</body>
</html>
