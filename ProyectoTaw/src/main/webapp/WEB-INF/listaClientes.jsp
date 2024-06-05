<%@ page import="es.uma.proyectotaw.entity.UsuarioEntity" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: carlossgaalvez
  Date: 11/5/24
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>

<%
    List<UsuarioEntity> listaClientes = (List<UsuarioEntity>) request.getAttribute("listaClientes");
    List<UsuarioEntity> entrenadoresBodyBuilding = (List<UsuarioEntity>) request.getAttribute("entrenadoresBodyBuilding");
    List<UsuarioEntity> entrenadoresCrossTraining = (List<UsuarioEntity>) request.getAttribute("entrenadoresCrossTraining");
%>

<html>
<head>
    <title>TrainingGym</title>
    <link rel="stylesheet" href="/styles/general.css">
    <link rel="stylesheet" href="/styles/listaClientes.css">
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
        <li><a href="/adminMain/clientes" id="activo">Clientes</a></li>
        <li><a href="/adminMain/solicitudes">Solicitudes</a></li>
        <li><a href="/adminMain/ejercicios">Ejercicios</a></li>
        <li><a href="/inicio" class="cerrar-sesion">Cerrar Sesión</a></li>
    </ul>
</nav>
<div class="imagen-fondo">
    <div class="capa-gris"></div>
    <h1 class="encabezado">Lista de clientes</h1> </br>

    <div class="contenido">
        <table>
            <%for(UsuarioEntity u : listaClientes){%>
            <tr>
                <form action="" method="post">
                    <th><%=u.getDni()%></th>
                    <th><%=u.getNombre()%> <%=u.getApellidos()%></th>
                    <th><%=u.getGenero()%></th>
                    <th>entrenador
                        <select name="idEntrenador">
                            <option value="" >Sin Entrenador</option>
                            <%if(u.getTipoEntrenamiento().getId() == 1){
                                for(UsuarioEntity e : entrenadoresBodyBuilding){
                                    String seleccionado = "";
                                    if(u.getEntrenador() != null && e.equals(u.getEntrenador())) {
                                        seleccionado="selected";
                                    }%>
                                    <option value="<%=e.getId()%>" <%=seleccionado%>><%=e.getNombre()%> <%=e.getApellidos()%></option>
                                <%}
                            } else {
                                for(UsuarioEntity e : entrenadoresCrossTraining){
                                    String seleccionado = "";
                                    if(u.getEntrenador() != null && e.getId() == u.getEntrenador().getId())seleccionado="selected";%>
                                    <option value="<%=e.getId()%>"><%=e.getNombre()%> <%=e.getApellidos()%></option>
                                <%}
                            }%>
                        </select>
                    </th>
                    <th><input type="submit" value="Guardar"></th>
                    <th><a href="/adminMain/cliente/borrar/<%=u.getId()%>">Borrar</a></th>
                </form>
            </tr>
            <%}%>
        </table>
    </div>
</div>
</body>
</html>
