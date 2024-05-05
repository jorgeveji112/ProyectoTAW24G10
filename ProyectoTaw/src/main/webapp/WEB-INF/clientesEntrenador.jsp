<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 30/04/2024
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%
    List<UsuarioEntity> listaClientes = (List<UsuarioEntity>) request.getAttribute("clientes");
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
            <div class="contenedor-cliente ">
                <div class="cliente-encabezado grid-container">
                    <div class="info-cliente">
                        <p class="cliente-nombre"><b>Nombre</b></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><b>Apellidos</b></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><b>Fecha de Nacimiento</b></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><b>Correo</b></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><b>Teléfono</b></p>
                    </div>
                    <div class="botones-cliente"></div>
                </div>
                <div class="lista-clientes">
                <%
                    for (UsuarioEntity cliente: listaClientes) {
                %>
                <div class="cliente-item">
                    <div class="info-cliente">
                        <p class="cliente-detalle"><%= cliente.getNombre()%></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><%= cliente.getApellidos()%></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><%= cliente.getFechaNacimiento()%></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><%= cliente.getCorreo()%></p>
                    </div>
                    <div class="info-cliente">
                        <p class="cliente-detalle"><%= cliente.getTelefono()%></p>
                    </div>
                    <div class="botones-cliente">
                        <form action="ver-cliente" method="post">
                            <input type="hidden" name="cliente" value="<%= cliente %>">
                            <button type="submit">Info. Cliente</button>
                        </form>
                        <form action="ver-entrenamiento" method="post">
                            <input type="hidden" name="cliente" value="<%= cliente %>">
                            <button type="submit">Entrenamiento</button>
                        </form>
                    </div>
                </div>
                    <div class="cliente-item">
                        <div class="info-cliente">
                            <p class="cliente-detalle"><%= cliente.getNombre()%></p>
                        </div>
                        <div class="info-cliente">
                            <p class="cliente-detalle"><%= cliente.getApellidos()%></p>
                        </div>
                        <div class="info-cliente">
                            <p class="cliente-detalle"><%= cliente.getFechaNacimiento()%></p>
                        </div>
                        <div class="info-cliente">
                            <p class="cliente-detalle"><%= cliente.getCorreo()%></p>
                        </div>
                        <div class="info-cliente">
                            <p class="cliente-detalle"><%= cliente.getTelefono()%></p>
                        </div>
                        <div class="botones-cliente">
                            <form action="ver-cliente" method="post">
                                <input type="hidden" name="cliente" value="<%= cliente %>">
                                <button type="submit">Info. Cliente</button>
                            </form>
                            <form action="ver-entrenamiento" method="post">
                                <input type="hidden" name="cliente" value="<%= cliente %>">
                                <button type="submit">Entrenamiento</button>
                            </form>
                        </div>
                    </div>
                <%
                    }
                %>
                </div>
        </div>
        </div>

    </div>
</body>
</html>
