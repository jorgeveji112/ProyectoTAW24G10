<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.proyectotaw.entity.ClienteEntity" %>
<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="es.uma.proyectotaw.entity.RutinaAsignadaEntity" %>

<%
    RutinaAsignadaEntity rutinaasignada = (RutinaAsignadaEntity) request.getAttribute("rutinaAsignada");
    ClienteEntity cliente = (ClienteEntity) request.getAttribute("cliente");
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
        <div class="session">
            <h2>Sesión número 1</h2>
            <button>Ver ejercicios sesión</button>
            <label for="sesion 1"></label><input id="sesion 1" type="checkbox">
        </div>
        <div class="session">
            <h2>Sesión número 2</h2>
            <button>Ver ejercicios sesión</button>
            <label for="sesion 2"></label><input id="sesion 2" type="checkbox">
        </div>
        <div class="session">
            <h2>Sesión número 3</h2>
            <button>Ver ejercicios sesión</button>
            <label for="sesion 2"></label><input id="sesion 3" type="checkbox">
        </div>
    </div>
</div>
</body>
</html>
