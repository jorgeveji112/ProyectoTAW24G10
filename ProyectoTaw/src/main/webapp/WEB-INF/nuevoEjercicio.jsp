<%@ page import="es.uma.proyectotaw.entity.TipoentrenamientoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.TipoejerciciobodybuildingEntity" %>
<%@ page import="es.uma.proyectotaw.entity.TipoejerciciocrosstrainingEntity" %><%--
 Created by IntelliJ IDEA.
  User: carlossgaalvez
  Date: 5/6/24
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>

<%
    List<TipoentrenamientoEntity> listaTiposEntrenamiento = (List<TipoentrenamientoEntity>) request.getAttribute("listaTiposEntrenamiento");
    List<TipoejerciciobodybuildingEntity> listaTiposEjercicioBodyBuilding = (List<TipoejerciciobodybuildingEntity>) request.getAttribute("listaTiposEjercicioBodyBuilding");
    List<TipoejerciciocrosstrainingEntity> listaTiposEjercicioCrossTraining = (List<TipoejerciciocrosstrainingEntity>) request.getAttribute("listaTiposEjercicioCrossTraining");
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
                            <%for (TipoentrenamientoEntity t : listaTiposEntrenamiento) {%>
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
                            <%for (TipoejerciciobodybuildingEntity t : listaTiposEjercicioBodyBuilding) {%>
                            <option value="bb_<%=t.getId()%>"><%=t.getTipo()%></option>
                            <%}%>
                            <%for (TipoejerciciocrosstrainingEntity t : listaTiposEjercicioCrossTraining) {%>
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
