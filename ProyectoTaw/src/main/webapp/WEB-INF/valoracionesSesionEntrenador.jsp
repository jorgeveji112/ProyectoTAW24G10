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
    String semanaString = semana.toString();
    SesionentrenamientoEntity sesion = (SesionentrenamientoEntity) request.getAttribute("sesion");
    List<SesionejercicioEntity> sesionesEjercicio = (List<SesionejercicioEntity>) request.getAttribute("sesionesEjercicio");
    List<ValoracionEntity> valoraciones = (List<ValoracionEntity>) request.getAttribute("valoraciones");
    LocalDate semanaAnterior = semana.minusDays(7);
    String semanaAnteriorString = semanaAnterior.toString();
    LocalDate semanaSiguiente = semana.plusDays(7);
    String semanaSiguienteString = semanaSiguiente.toString();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/valoracionesSesionEntrenador.css">
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
    <div class="contenedor-rutina-titulo">
        <h1><%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1>
        <div class="contenido">
            <div class="fecha">
                <img src="/img/flecha-izquierda.png" alt="flecha izquierda" class="icono" onclick="window.location.href='/entrenadorMain/clientes/entrenamiento?id=<%=cliente.getId()%>&fecha=<%=semanaAnteriorString%>'">
                <p>Sem. <%=semana.getDayOfMonth()%>/<%=semana.getMonthValue()%></p>
                <img src="/img/flecha-derecha.png" alt="flecha derecha" class="icono" onclick="window.location.href='/entrenadorMain/clientes/entrenamiento?id=<%=cliente.getId()%>&fecha=<%=semanaSiguienteString%>'">
            </div>

            <div class="info-rutina">
                <p id="label-nombre">
                    <span class="label"> <%=rutinaAsignada.getRutinaPredefinida().getNombre()%> > <%= sesion.getNombre() %></span>
                </p>
                <div class="div-sesiones">
                    <p>Sesiones:</p>

                    <div id="lista-sesiones">
                        <%
                            for (SesionejercicioEntity sesionEjercicio : sesionesEjercicio) {
                                ValoracionEntity valoracion = null;
                                for (ValoracionEntity val : valoraciones) {
                                    if (val.getSesionejercicio().equals(sesionEjercicio)) {
                                        valoracion = val;
                                        break;
                                    }
                                }
                                int puntuacion = 0;
                                if(valoracion != null){
                                    puntuacion = valoracion.getPuntuacion();
                                }
                                String comentario = (valoracion != null && valoracion.getDescripcion() != null) ? valoracion.getDescripcion() : cliente.getNombre() + " " + cliente.getApellidos() + " no ha escrito ningún comentario todavía.";
                        %>
                        <div class="valoracion">
                            <div class="sesion">
                                    <p class="nombre-sesion"><%=sesionEjercicio.getEjercicio().getNombre()%></p>
                                    <div class="contenedor-iconos">
                                        <% for (int i = 0; i < puntuacion; i++) { %>
                                        <img src="/img/estrellaamarilla.png" alt="Estrella Amarilla" class="img-icono">
                                        <% } %>

                                        <%-- Mostrar estrellas blancas para completar hasta 5 --%>
                                        <% for (int i = puntuacion; i < 5; i++) { %>
                                        <img src="/img/estrellablanca.png" alt="Estrella Blanca" class="img-icono">
                                        <% } %>
                                    </div>
                            </div>
                            <div class="sesion">
                                <div class="intensidad-div">
                                    <p><input type="text" readonly value="<%= (sesionEjercicio.getSeries() != null) ? sesionEjercicio.getSeries() : "-" %>" class="intensidad" name="series"> series</p>
                                    <p><input type="text" readonly value="<%= (sesionEjercicio.getRepeticiones() != null) ? sesionEjercicio.getRepeticiones() : "-" %>" class="intensidad" name="repeticiones"> repeticiones</p>
                                    <p><input type="text" readonly value="<%= (sesionEjercicio.getDuracion() != null) ? sesionEjercicio.getDuracion() : "-" %>" class="intensidad" name="duracion"> min</p>
                                </div>
                                <div class="descripcion-div">
                                    Comentario:
                                    <textarea readonly class="descripcion" name="descripcion"><%= comentario %></textarea>
                                </div>
                            </div>
                        </div>
                        <%
                            }
                        %>

                    </div>
                </div>

            </div>

        </div>
    </div>

</body>
</html>