<%--
  Creador: Jorge Velázquez Jiménez
--%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.uma.proyectotaw.entity.ClienteEntity" %>
<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %>

<%  ClienteEntity cliente = (ClienteEntity) request.getAttribute("cliente");
    UsuarioEntity usuario = cliente.getUsuario();
    LocalDate fecha = LocalDate.now();
    LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
    String fechaLunes = lunes.toString();
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
        <li><a href="/clienteMain/inicio" >Inicio</a></li>
        <li><a href="/clienteMain/perfil" id="activo">Perfil</a></li>
        <li><a href="/clienteMain/rutina?fecha=<%=fechaLunes%>">Rutina</a></li>
        <li><a href="/clienteMain/desarrollo?fecha=<%=fechaLunes%>">Desarrollo</a></li>
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
        <form method="post">
            <h1 class="section-title">Datos de Contacto</h1>
            <div class="form-group">
                <label>Correo Electrónico:</label>
                <input type="text" name="correo" value="<%=usuario.getCorreo()%>" >
            </div>
            <div class="form-group">
                <label>Número de Teléfono:</label>
                <input type="text" name="telefono" value="<%=usuario.getTelefono()%>" >
            </div>

            <h1 class="section-title">Datos Físicos y Objetivos</h1>
            <div class="form-group half-width">
                <label>Altura:</label>
                <input type="text" name="altura" value="<%=cliente.getAltura()%>">
            </div>
            <div class="form-group half-width">
                <label>Peso:</label>
                <input type="text" name="peso" value="<%=cliente.getPeso()%>" >
            </div>
            <div class="form-group">
                <label>Objetivos:</label>
                <textarea rows="3" name="objetivos" ><%=cliente.getObjetivos()%></textarea>
            </div>
            <input class="submit" type="submit" value="Guardar Cambios">
        </form>

    </div>
</div>
</body>
</html>
