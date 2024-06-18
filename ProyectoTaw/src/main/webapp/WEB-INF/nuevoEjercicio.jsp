<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.dto.TipoentrenamientoDTO" %>
<%@ page import="es.uma.proyectotaw.dto.TipoejerciciobodybuildingDTO" %>
<%@ page import="es.uma.proyectotaw.dto.TipoejerciciocrosstrainingDTO" %>
<%--
    Realizado por Carlos Gálvez Bravo
--%>
<%
    List<TipoentrenamientoDTO> listaTiposEntrenamiento = (List<TipoentrenamientoDTO>) request.getAttribute("listaTiposEntrenamiento");
    List<TipoejerciciobodybuildingDTO> listaTiposEjercicioBodyBuilding = (List<TipoejerciciobodybuildingDTO>) request.getAttribute("listaTiposEjercicioBodyBuilding");
    List<TipoejerciciocrosstrainingDTO> listaTiposEjercicioCrossTraining = (List<TipoejerciciocrosstrainingDTO>) request.getAttribute("listaTiposEjercicioCrossTraining");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/nuevoEjercicio.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="logo"><img src="/img/logoGym.png"></div>
    <ul class="enlaces">
        <li><a href="/adminMain/inicio">Inicio</a></li>
        <li><a href="/adminMain/entrenadores">Entrenadores</a></li>
        <li><a href="/adminMain/clientes">Clientes</a></li>
        <li><a href="/adminMain/solicitudes">Solicitudes</a></li>
        <li><a href="/adminMain/ejercicios" id="activo">Ejercicios</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <h1 class="encabezado">Nuevo ejercicio:</h1> </br>

    <div class="contenido">
        <form class="formulario" action="/adminMain/crearEjercicio" method="post">
            <table>
                <tr>
                    <th>Nombre:</th>
                    <th><input class="inputs" name="nombre"></th>
                    <th></th>
                </tr>
                <tr>
                    <th>Tipo de entrenamiento:</th>
                    <th>
                        <select class="sel" name="tipoentrenamiento">
                            <%for (TipoentrenamientoDTO t : listaTiposEntrenamiento) {%>
                            <option value="<%=t.getId()%>"><%=t.getTipo()%></option>
                            <%}%>
                        </select>
                    </th>
                    <th></th>
                </tr>
                <tr>
                    <th>Tipo de ejercicio:</th>
                    <th>
                        <select class="sel" name="tipoejercicio">
                            <%for (TipoejerciciobodybuildingDTO t : listaTiposEjercicioBodyBuilding) {%>
                            <option value="bb_<%=t.getId()%>"><%=t.getTipo()%></option>
                            <%}%>
                            <%for (TipoejerciciocrosstrainingDTO t : listaTiposEjercicioCrossTraining) {%>
                            <option value="cs_<%=t.getId()%>"><%=t.getTipo()%></option>
                            <%}%>
                        </select>
                    </th>
                    <th></th>
                </tr>
                <tr>
                    <th>Video:</th>
                    <th><input class="inputs" name="video"></th>
                    <th></th>
                </tr>
                <tr>
                    <th>Descripción:</th>
                    <th><input class="inputs" name="descripcion"></th>
                    <th><input class="crear" type="submit" value="Crear"></th>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>
