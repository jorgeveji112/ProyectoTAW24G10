<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.*" %>
<%@ page import="java.time.LocalDate" %><%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 30/04/2024
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%
    UsuarioEntity cliente = (UsuarioEntity) request.getAttribute("cliente");
    RutinaAsignadaEntity rutinaAsignada = (RutinaAsignadaEntity) request.getAttribute("rutinaAsignada");
    LocalDate semana = (LocalDate) request.getAttribute("semana");
    List<RutinaPredefinidaEntity> rutinasEntrenador = (List<RutinaPredefinidaEntity>) request.getAttribute("rutinasEntrenador");
    LocalDate semanaAnterior = semana.minusDays(7);
    String semanaAnteriorString = semanaAnterior.toString();
    LocalDate semanaSiguiente = semana.plusDays(7);
    String semanaSiguienteString = semanaSiguiente.toString();
    List<RutinaSesionentrenamientoEntity> rutinasSesiones = (List<RutinaSesionentrenamientoEntity>) request.getAttribute("rutinasSesiones");
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
    <div class="logo"><img src="/img/logoGym.png"></div>
    <ul class="enlaces">
        <li><a href="/entrenadorMain/inicio" >Inicio</a></li>
        <li><a href="/entrenadorMain/rutinas">Rutinas</a></li>
        <li><a href="/entrenadorMain/sesiones">Sesiones</a></li>
        <li><a href="/entrenadorMain/clientes" id="activo">Clientes</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div id="modalAsignarRutina" class="modal">
        <div class="modal-content">
            <h2>Lista de Rutinas Semanales</h2>
            <div class="lista-rutinas-modal">
                <% for (RutinaPredefinidaEntity rutina : rutinasEntrenador) { %>
                <div class="rutina-modal">
                    <p><%= rutina.getNombre() %></p>
                    <form action="/entrenadorMain/clientes/entrenamiento/asignarRutina" method="post">
                        <input type="hidden" name="rutinaId" value="<%= rutina.getId() %>">
                        <input type="hidden" name="usuarioId" value="<%= cliente.getId() %>">
                        <input type="hidden" name="fecha" value="<%= semana %>">
                        <button type="submit">Asignar</button>
                    </form>
                </div>
                <% } %>
            </div>
        </div>
    </div>
    <div class="contenedor-rutina-titulo">
        <h1><%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1>
        <div class="contenido">
            <div class="fecha">
                <img src="/img/flecha-izquierda.png" alt="flecha izquierda" class="icono" onclick="window.location.href='/entrenadorMain/clientes/entrenamiento?id=<%=cliente.getId()%>&fecha=<%=semanaAnteriorString%>'">
                <p>Sem. <%=semana.getDayOfMonth()%>/<%=semana.getMonthValue()%></p>
                <img src="/img/flecha-derecha.png" alt="flecha derecha" class="icono" onclick="window.location.href='/entrenadorMain/clientes/entrenamiento?id=<%=cliente.getId()%>&fecha=<%=semanaSiguienteString%>'">
            </div>
            <%
                if(rutinaAsignada != null){
            %>
                    <div class="info-rutina">
                        <p id="label-nombre">
                            Nombre de la Rutina:
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
                                    <input type="hidden" name="sesiones" value="<%=rutinaHasSesion.getSesionentrenamiento().getId()%>">
                                    <button class="btn-ver-sesion">Ver Sesión</button>

                                    <div class="contenedor-iconos">
                                        <img src="/img/flecha-arriba.png" alt="Icono Subir" class="img-icono" onclick="moverSesion(this, 'arriba')">
                                        <img src="/img/flecha-abajo.png" alt="Icono Bajar" class="img-icono" onclick="moverSesion(this, 'abajo')">
                                        <img src="/img/borrar.png" alt="Icono Borrar" class="img-icono" onclick="borrarSesion(this)">
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
                        <button id="btn-asignar-rutina" onclick="mostrarModal()">Asignar Rutina</button>
                    </div>
            <%
                }
            %>
        </div>

    </div>
</div>
<script src="/scripts/asignarRutinaEntrenador.js"></script>
</body>
</html>