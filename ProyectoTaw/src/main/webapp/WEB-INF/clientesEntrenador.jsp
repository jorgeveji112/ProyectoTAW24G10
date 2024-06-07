<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.DayOfWeek" %><%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 30/04/2024
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%
    List<UsuarioEntity> listaClientes = (List<UsuarioEntity>) request.getAttribute("clientes");
    LocalDate fecha = LocalDate.now();
    LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
    String fechaLunes = lunes.toString();
    String filtro = (String) request.getAttribute("filtro");
    if(filtro == null ){
        filtro = "";
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/clientesEntrenador.css">
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
        <div class="contenedor-cliente-titulo">
            <h1>Clientes</h1>
            <div class="contenido">
                <div class="tabla">
                <table>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>Fecha de Nacimiento</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th></th>
                        <th></th>
                    <%
                        for (UsuarioEntity cliente: listaClientes) {
                    %>

                        <tr>
                            <td><%= cliente.getNombre()%></td>
                            <td><%= cliente.getApellidos()%></td>
                            <td><%= cliente.getFechaNacimiento()%></td>
                            <td><%= cliente.getCorreo()%></td>
                            <td><%= cliente.getTelefono()%></td>
                            <td>
                                <div class="btn-cliente">
                                    <button onclick="window.location.href='/entrenadorMain/clientes/perfil?id=<%=cliente.getId()%>'">Informacion del cliente</button>
                                </div>
                            </td>
                            <td>
                                <div class="btn-cliente">
                                    <button onclick="window.location.href='/entrenadorMain/clientes/entrenamiento?id=<%=cliente.getId()%>&fecha=<%=fechaLunes%>'">Entrenamiento</button>
                                </div>
                            </td>

                        </tr>
                    <%}%>
                    </table>
                </div>
                <div class="filtro-container">
                    <div class="filtro">
                        <form class="formulario" action="/entrenadorMain/clientes/filtrar" method="post">
                            <p>Escribe nombre, apellidos o DNI</p>
                            <input class="entrada" name="filtro" value="<%=filtro%>"> </br>
                            <div class="btn-cliente">
                            <input class="filtrar" type="submit" value="Filtrar">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
