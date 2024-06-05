<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.proyectotaw.entity.ClienteEntity" %>
<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>

<%  ClienteEntity cliente = (ClienteEntity) request.getAttribute("cliente");
    UsuarioEntity usuario = cliente.getUsuario();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/perfilCliente.css">
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

<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="container">
        <h1 class="section-title">Datos Personales</h1>
        <div class="form-group half-width">
            <label>Nombre:</label>
            <input type="text" value="<%=usuario.getNombre()%>" readonly>
        </div>
        <div class="form-group half-width">
            <label>Apellidos:</label>
            <input type="text" value="<%=usuario.getApellidos()%>" readonly>
        </div>
        <div class="form-group half-width">
            <label>Fecha de Nacimiento:</label>
            <input type="text" value="<%=usuario.getFechaNacimiento()%>" readonly>
        </div>
        <div class="form-group half-width">
            <label>Sexo:</label>
            <input type="text" value="<%=usuario.getGenero()%>" readonly>
        </div>
        <div class="form-group">
            <label>DNI:</label>
            <input type="text" value="<%=usuario.getDni()%>" readonly>
        </div>

        <h1 class="section-title">Datos de Contacto</h1>
        <div class="form-group">
            <label>Correo Electrónico:</label>
            <input type="text" value="<%=usuario.getCorreo()%>" readonly>
        </div>
        <div class="form-group">
            <label>Número de Teléfono:</label>
            <input type="text" value="<%=usuario.getTelefono()%>" readonly>
        </div>

        <h1 class="section-title">Datos Físicos y Objetivos</h1>
        <div class="form-group half-width">
            <label>Altura:</label>
            <input type="text" value="<%=cliente.getAltura() + " cm"%>" readonly>
        </div>
        <div class="form-group half-width">
            <label>Peso:</label>
            <input type="text" value="<%=cliente.getPeso() + " cm"%>" readonly>
        </div>
        <div class="form-group">
            <label>Objetivos:</label>
            <textarea rows="3" readonly><%=cliente.getObjetivos()%></textarea>
        </div>
    </div>
</div>
</body>
</html>
