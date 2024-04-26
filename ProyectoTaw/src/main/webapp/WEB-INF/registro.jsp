<%--
  Created by IntelliJ IDEA.
  User: BEEP
  Date: 26/04/2024
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/acceso.css">
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
    <nav>
        <div class="logo"><img src="/img/logoGym.png"></div>
        <ul class="enlaces">
            <li><a href="inicio">Inicio</a></li>
            <li><a href="trabaja">Trabaja con Nosotros</a></li>
            <li><a href="acceso" class="acceso">Acceso</a></li>
        </ul>
    </nav>
    <div class="imagen-fondo">
        <div class="capa-gris"></div>
        <div class="contenido">
            <h2>Únete a TraniningGym</h2>
            <form action="/login" method="post">
                <div>
                    <label for="username">Nombre de usuario:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div>
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">REGISTRARSE</button>
            </form>
            <p>¿Ya tienes cuenta? <a href="acceso" id="enlace-registrarse">Inicia Sesión</a></p>
        </div>
    </div>
</body>
</html>
