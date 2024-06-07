<%--
  Creador: Jorge Velázquez Jiménez
--%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.*" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="java.time.LocalDate" %>

<%
    SesionentrenamientoEntity sesion = (SesionentrenamientoEntity) request.getAttribute("sesion");

    List<SesionentrenamientoHasSesionejercicioEntity> ejercicios  = (List<SesionentrenamientoHasSesionejercicioEntity>) request.getAttribute("ejercicios");
    List<ValoracionEntity> valoraciones  = (List<ValoracionEntity>) request.getAttribute("valoraciones");
    RutinaAsignadaEntity rutina = (RutinaAsignadaEntity) request.getAttribute("rutinaAsignada");

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
    <link rel="stylesheet" href="/styles/rutinaCliente.css">
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
        <li><a href="/clienteMain/rutina?fecha=<%=fechaLunes%>" id="activo">Rutina</a></li>
        <li><a href="/clienteMain/desarrollo?fecha=<%=fechaLunes%>">Desarrollo</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<h1><%=sesion.getNombre()%></h1>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="sesiones">
        <%
            for(SesionentrenamientoHasSesionejercicioEntity ejercicio : ejercicios){
        %>
        <div class="session">
            <h2><%=ejercicio.getSesionejercicio().getEjercicio().getNombre()%></h2>
            <button onclick="window.location.href='/clienteMain/rutina/sesion/ejercicio?rutinaId=<%=rutina.getId()%>&sesionId=<%=sesion.getId()%>&id=<%=ejercicio.getSesionejercicio().getId()%>'"> Ver ejercicio </button>
            <%
                    Boolean valorado = false;
                    for (ValoracionEntity val : valoraciones) {
                        if (val.getSesionejercicio().equals(ejercicio.getSesionejercicio())) {
                            valorado = true;
                            break;
                        }
                    }

                    if(valorado){
            %>
            <label class="custom-checkbox">
                <input type="checkbox" checked disabled>
                <span class="checkmark"></span>
            </label>
            <%

            }else{
            %>
            <label class="custom-checkbox">
                <input type="checkbox" disabled>
                <span class="checkmark"></span>
            </label>
                <%

                    }
                %>
        </div>
        <%
            };
        %>
    </div>
</div>
</body>
</html>
