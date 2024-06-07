<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.proyectotaw.entity.*" %>

<%
    SesionejercicioEntity ejercicio = (SesionejercicioEntity) request.getAttribute("ejercicio");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/ejercicioCliente.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav>
    <div class="logo"><img src="/img/logoGym.png" alt="TrainingGym Logo"></div>
    <ul class="enlaces">
        <li><a href="/clienteMain/inicio" id="activo">Inicio</a></li>
        <li><a href="/clienteMain/perfil">Perfil</a></li>
        <li><a href="/clienteMain/rutina">Rutina</a></li>
        <li><a href="/clienteMain/desarrollo">Desarrollo</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>

<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <div class="card">
        <h1><%=ejercicio.getEjercicio().getNombre()%></h1>
        <label for="descripcion">Descripcion del ejercicio</label><textarea id="descripcion" class="description" readonly><%=ejercicio.getEjercicio().getDescripcion()%></textarea>
        <div class="input-group">
            <label for="series">Series</label><input type="text" id="series" value="<%=ejercicio.getSeries()%>" readonly>
            <label for="repeticiones">Repeticiones</label><input type="text" id="repeticiones" value="<%=ejercicio.getRepeticiones()%>" readonly>
            <label for="duración">Duración</label><input type="text" id="duración" value="<%=ejercicio.getDuracion()%>" readonly>
        </div>
        <div class="button-group">
            <button >Video ejercicio</button>
            <button onclick="document.getElementById('ratingDialog').showModal()">Completar ejercicio</button>
        </div>
    </div>
    <dialog id="ratingDialog">
        <h1>Ejercicio número 1</h1>
        <form id="ratingForm" method="post">
            <div class="star-rating">
                <span class="star" data-value="1">&#9733;</span>
                <span class="star" data-value="2">&#9733;</span>
                <span class="star" data-value="3">&#9733;</span>
                <span class="star" data-value="4">&#9733;</span>
                <span class="star" data-value="5">&#9733;</span>
            </div>
            <div>
                <label for="comment">Comenta qué te ha parecido el ejercicio y si has podido completarlo:</label>
                <textarea id="comment" class="comment" name="comentario"></textarea>
            </div>
            <input type="hidden" id="ejercicio" name="ejercicio" value="<%=ejercicio.getId()%>">
            <input type="hidden" id="rating" name="rating" value="0">
            <input type="submit" class="submit-button">
        </form>
    </dialog>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const stars = document.querySelectorAll('.star');
        stars.forEach(star => {
            star.addEventListener('click', function() {
                const rating = this.getAttribute('data-value');
                document.getElementById('rating').value = rating;
                stars.forEach((s, index) => {
                    if (index < rating) {
                        s.classList.add('filled');
                    } else {
                        s.classList.remove('filled');
                    }
                });
            });
        });

        const ratingForm = document.getElementById('ratingForm');
        ratingForm.addEventListener('submit', function(event) {
            const filledStars = document.querySelectorAll('.star.filled').length;
            document.getElementById('rating').value = filledStars;
        });
    });
</script>
</body>
</html>
