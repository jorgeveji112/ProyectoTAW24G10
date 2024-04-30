<%@ page import="es.uma.proyectotaw.entity.SesionentrenamientoEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 30/04/2024
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%
    List<SesionentrenamientoEntity> listaSesiones = (List<SesionentrenamientoEntity>) request.getAttribute("sesiones");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/sesionesEntrenador.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="logo"><img src="/img/logoGym.png"></div>
    <ul class="enlaces">
        <li><a href="/entrenadorMain/inicio" >Inicio</a></li>
        <li><a href="/entrenadorMain/rutinas">Rutinas</a></li>
        <li><a href="/entrenadorMain/sesiones" id="activo">Sesiones</a></li>
        <li><a href="/entrenadorMain/clientes">Clientes</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="contenedor-crear-sesion">
        <button class="crear-sesion">Crear Sesión</button>
        <div class="contenedor-sesion">
            <%
                for (SesionentrenamientoEntity sesion: listaSesiones) {
            %>
            <div class="sesion-item">
                <p class="sesion-nombre"><%= sesion.getNombre()%></p>
                <button class="ver-sesion">Ver Sesión</button>
                <button class="borrar-sesion">Borrar Sesión</button>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>
</body>
</html>

