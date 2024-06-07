<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.proyectotaw.entity.ClienteEntity" %>
<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="es.uma.proyectotaw.entity.RutinaAsignadaEntity" %>
<%@ page import="es.uma.proyectotaw.entity.RutinaSesionentrenamientoEntity" %>
<%@ page import="java.util.List" %>

<%
    RutinaAsignadaEntity rutinaasignada = (RutinaAsignadaEntity) request.getAttribute("rutinaAsignada");
    ClienteEntity cliente = (ClienteEntity) request.getAttribute("cliente");

    List<RutinaSesionentrenamientoEntity> sesiones = (List<RutinaSesionentrenamientoEntity>) request.getAttribute("sesiones");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/rutinaCliente.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="logo"><img src="/img/logoGym.png" alt="TrainingGym Logo"></div>
    <ul class="enlaces">
        <li><a href="/clienteMain/inicio" id="activo">Inicio</a></li>
        <li><a href="/clienteMain/perfil">Perfil</a></li>
        <li><a href="/clienteMain/rutina">Rutina</a></li>
        <li><a href="/clienteMain/desarrollo">Desarrollo</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<h1><%=rutinaasignada.getRutinaPredefinida().getNombre()%></h1>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="sesiones">
        <%
            for(RutinaSesionentrenamientoEntity sesion : sesiones){
        %>
        <div class="session">
            <h2><%=sesion.getSesionentrenamiento().getNombre()%></h2>
            <button onclick="window.location.href='/clienteMain/rutina/sesion?id=<%=sesion.getSesionentrenamiento().getId()%>'"> Ver ejercicios sesión</button>
            <label for="sesion <%=sesion.getSesionentrenamiento().getId()%>"></label><input id="sesion <%=sesion.getSesionentrenamiento().getId()%>" type="checkbox">
        </div>
        <%
            };
        %>
    </div>
</div>
</body>
</html>
