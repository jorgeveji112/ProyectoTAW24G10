<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.*" %>

<%
    SesionentrenamientoEntity sesion = (SesionentrenamientoEntity) request.getAttribute("sesion");

    List<SesionentrenamientoHasSesionejercicioEntity> ejercicios  = (List<SesionentrenamientoHasSesionejercicioEntity>) request.getAttribute("ejercicios");
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
        <li><a href="/clienteMain/inicio" >Inicio</a></li>
        <li><a href="/clienteMain/perfil">Perfil</a></li>
        <li><a href="/clienteMain/rutina" id="activo">Rutina</a></li>
        <li><a href="/clienteMain/desarrollo">Desarrollo</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<h1><%=sesion.getNombre()%></h1>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="sesiones">
        <%
            for(SesionentrenamientoHasSesionejercicioEntity ejercicio : ejercicios){
        %>
        <div class="session">
            <h2><%=ejercicio.getSesionejercicio().getEjercicio().getNombre()%></h2>
            <button onclick="window.location.href='/clienteMain/rutina/sesion/ejercicio?id=<%=ejercicio.getSesionejercicio().getId()%>'"> Ver ejercicio </button>
            <label for="sesion <%=ejercicio.getSesionejercicio().getId()%>"></label><input id="sesion <%=ejercicio.getSesionejercicio().getId()%>" type="checkbox">
        </div>
        <%
            };
        %>
    </div>
</div>
</body>
</html>
