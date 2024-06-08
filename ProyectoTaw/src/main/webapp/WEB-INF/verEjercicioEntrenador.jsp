<%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 09/05/2024
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="es.uma.proyectotaw.entity.SesionentrenamientoEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.SesionentrenamientoHasSesionejercicioEntity" %>
<%@ page import="es.uma.proyectotaw.entity.EjercicioEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    EjercicioEntity ejercicio = (EjercicioEntity) request.getAttribute("ejercicio");
%>
<html>
<head>
    <title>Training Gym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/verSesionEntrenador.css">
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


    <div class="contenedor-sesion-titulo">
        <h1>Ejercicio</h1>

    </div>
</div>
<script src="/scripts/verSesionEntrenador.js"></script>
</body>
</html>
