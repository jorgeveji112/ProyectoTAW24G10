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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SesionentrenamientoEntity sesion = (SesionentrenamientoEntity) request.getAttribute("sesion");
    List<SesionentrenamientoHasSesionejercicioEntity> listaSesionHasSesion = (List<SesionentrenamientoHasSesionejercicioEntity>) request.getAttribute("listaSesionHasSesion");
    boolean esEditar = (sesion.getId() != -1);
    String nombre = "", descripcion = "";

    if (esEditar) {
        nombre = sesion.getNombre();
        descripcion = sesion.getDescripcion();
    }
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
            <h1>Sesión de Entrenamiento</h1>
            <div class="contenido">
                <form method="post" action="/entrenadorMain/sesiones/guardar">
                    <label id="label-nombre">
                        Nombre:
                        <input type="text" name="nombre" value="<%=nombre%>" class="nombre">
                    </label>
                    <label id="label-descripcion">
                        Descripción:
                        <textarea name="descripcion"><%=descripcion%></textarea>
                    </label>
                    <div id="div-ejercicios">
                        <p>Ejercicios:</p>
                        <div id="lista-ejercicios">
                            <%
                                for (SesionentrenamientoHasSesionejercicioEntity sesionHasSesion : listaSesionHasSesion) {
                            %>
                            <div class="ejercicio">
                                <p class="nombre-ejercicio"><%=sesionHasSesion.getSesionejercicio().getEjercicio().getNombre()%></p>
                                <button class="btn-ver-ejercicio">Ver ejercicio</button>
                                <%
                                    if(sesionHasSesion.getSesionejercicio().getSeries() == null){
                                %>
                                <p><input type="text" value="-" class="intensidad">series</p>
                                <%}else{%>
                                <p><input type="text" value="<%=sesionHasSesion.getSesionejercicio().getSeries()%>" class="intensidad">series</p>
                                <%}
                                    if(sesionHasSesion.getSesionejercicio().getRepeticiones() == null){
                                %>
                                <p><input type="text" value="-" class="intensidad">repeticiones</p>
                                <%}else{%>
                                <p><input type="text" value="<%=sesionHasSesion.getSesionejercicio().getRepeticiones()%>" class="intensidad">repeticiones</p>
                                <%}
                                    if(sesionHasSesion.getSesionejercicio().getDuracion() == null){
                                %>
                                <p><input type="text" value="-" class="intensidad">min</p>
                                <%}else{%>
                                <p><input type="text" value="<%=sesionHasSesion.getSesionejercicio().getDuracion()%>" class="intensidad">min</p>
                                <%}%>
                                    <div class="contenedor-iconos">
                                        <img src="/img/flecha-arriba.png" alt="Icono Subir" class="img-icono">
                                        <img src="/img/flecha-abajo.png" alt="Icono Bajar" class="img-icono">
                                        <img src="/img/borrar.png" alt="Icono Borrar" class="img-icono">
                                    </div>

                            </div>
                            <%
                                }
                            %>

                        </div>
                    </div>
                    <input type="hidden" name="id" value="<%=sesion.getId()%>">
                    <input type="submit" value="Guardar">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
