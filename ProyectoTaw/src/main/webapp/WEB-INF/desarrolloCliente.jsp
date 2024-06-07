<%--
  Creador: Jorge Velázquez Jiménez
--%>



<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %><%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 30/04/2024
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%
    UsuarioEntity cliente = (UsuarioEntity) request.getAttribute("usuario");
    RutinaAsignadaEntity rutinaAsignada = (RutinaAsignadaEntity) request.getAttribute("rutinaAsignada");
    LocalDate semana = (LocalDate) request.getAttribute("semana");
    String semanaString = semana.toString();

    LocalDate semanaAnterior = semana.minusDays(7);
    String semanaAnteriorString = semanaAnterior.toString();
    LocalDate semanaSiguiente = semana.plusDays(7);
    String semanaSiguienteString = semanaSiguiente.toString();
    List<RutinaSesionentrenamientoEntity> rutinasSesiones = (List<RutinaSesionentrenamientoEntity>) request.getAttribute("rutinasSesiones");

    LocalDate fecha = LocalDate.now();
    LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
    String fechaLunes = lunes.toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/asignarRutinaEntrenador.css">
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
        <li><a href="/clienteMain/rutina?fecha=<%=fechaLunes%>" >Rutina</a></li>
        <li><a href="/clienteMain/desarrollo?fecha=<%=fechaLunes%>" id="activo">Desarrollo</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="contenedor-rutina-titulo">
        <h1><%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1>
        <div class="contenido">
            <div class="fecha">
                <img src="/img/flecha-izquierda.png" alt="flecha izquierda" class="icono" onclick="window.location.href='/clienteMain/desarrollo?fecha=<%=semanaAnteriorString%>'">
                <p>Sem. <%=semana.getDayOfMonth()%>/<%=semana.getMonthValue()%></p>
                <img src="/img/flecha-derecha.png" alt="flecha derecha" class="icono" onclick="window.location.href='/clienteMain/desarrollo?fecha=<%=semanaSiguienteString%>'">
            </div>
            <%
                if(rutinaAsignada != null){
            %>
            <div class="info-rutina">
                <p id="label-nombre">
                    <span class="label"> Nombre de la Rutina:</span>
                    <span class="nombre"><%=rutinaAsignada.getRutinaPredefinida().getNombre()%></span>
                </p>
                <p id="label-objetivos">
                    Objetivos de la Rutina:
                    <span class="objetivos"><%=rutinaAsignada.getRutinaPredefinida().getObjetivos()%></span>
                </p>
                <div id="div-sesiones">
                    <p>Sesiones:</p>

                    <div id="lista-sesiones">
                        <%
                            for (RutinaSesionentrenamientoEntity rutinaHasSesion : rutinasSesiones) {
                        %>
                        <div class="sesion">
                            <p class="nombre-sesion"><%=rutinaHasSesion.getSesionentrenamiento().getNombre()%></p>
                            <button class="btn-ver-sesion" onclick="window.location.href='/clienteMain/desarrollo/sesion?rutina=<%= rutinaAsignada.getId()%>&sesion=<%= rutinaHasSesion.getSesionentrenamiento().getId()%>'">Ver Sesión</button>

                            <div class="contenedor-iconos">
                                <img src="/img/estrellaamarilla.png" alt="Estrella Amarilla" class="img-icono">
                                <img src="/img/estrellaamarilla.png" alt="Estrella Amarilla" class="img-icono">
                                <img src="/img/estrellaamarilla.png" alt="Estrella Amarilla" class="img-icono">
                                <img src="/img/estrellablanca.png" alt="Estrella Blanca" class="img-icono">
                                <img src="/img/estrellablanca.png" alt="Estrella Blanca" class="img-icono">
                            </div>

                        </div>
                        <%
                            }
                        %>

                    </div>
                </div>
                <%
                }else{
                %>
                <div class="sin-rutina-div">
                    <p class="sin-rutina">No tiene ninguna rutina asignada todavía</p>
                </div>
                <%
                    }
                %>
    </div>
</body>
</html>