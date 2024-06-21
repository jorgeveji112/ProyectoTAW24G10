/*
        Creador: Jorge Velázquez Jiménez
*/
package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.dto.*;
import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.*;
import es.uma.proyectotaw.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
public class RutinaClienteController extends BaseController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RutinaAsignadaService rutinaAsignadaService;

    @Autowired
    private RutinaSesionentrenamientoService rutinaSesionentrenamientoService;

    @Autowired
    private SesionentrenamientoService sesionEntrenamientoService;

    @Autowired
    private SesionentrenamientoHasSesionejercicioService sesionentrenamientoHasSesionejercicioService;

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private SesionEjercicioService sesionEjercicioService;


    @GetMapping("/clienteMain/rutina")
    public String doRutina(@RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        ClienteDTO cliente = (ClienteDTO) session.getAttribute("cliente");
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);
        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorUsuarioYFecha(cliente.getUsuario(), fechaDate);
        if(rutinaAsignada != null){
            List<RutinaSesionentrenamientoDTO> rutinaSesionentrenamiento = rutinaSesionentrenamientoService.buscarPorRutinaPredefinidaOrdenadaPorPosicion(rutinaAsignada.getRutinaPredefinida());
            model.addAttribute("sesiones", rutinaSesionentrenamiento);
            model.addAttribute("rutinaAsignada", rutinaAsignada);
            model.addAttribute("cliente", cliente);
        } else{
            model.addAttribute("rutinaAsignada", rutinaAsignada);
            model.addAttribute("cliente", cliente);
        }


        return "rutinaCliente"; // Retorna el nombre de la vista de cliente
    }

    @GetMapping("/clienteMain/rutina/sesion")
    public String doSesion(@RequestParam("id") Integer sesionId, @RequestParam("rutinaId") Integer rutinaId, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoDTO sesionentrenamiento = sesionEntrenamientoService.buscarPorId(sesionId);
        UsuarioDTO cliente = (UsuarioDTO) session.getAttribute("usuario");
        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorId(rutinaId);
        List<SesionentrenamientoHasSesionejercicioDTO> ejercicios = sesionentrenamientoHasSesionejercicioService.buscarPorSesionentrenamientoOrdenadoPorPosicion(sesionentrenamiento.getId());
        List<SesionejercicioDTO> sesionesEjercicio = ejercicios.stream().map(SesionentrenamientoHasSesionejercicioDTO::getSesionejercicio).toList();
        List<ValoracionDTO>  valoraciones = valoracionService.buscarPorUsuarioYRutinaAsignadaYSesionejercicioDentro(cliente.getId(),rutinaAsignada.getId(), sesionesEjercicio);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("sesion", sesionentrenamiento);
        model.addAttribute("rutinaAsignada", rutinaAsignada);

        return "sesionCliente"; // Retorna el nombre de la vista de cliente
    }

    @GetMapping("/clienteMain/rutina/sesion/ejercicio")
    public String doEjercico(@RequestParam("id") Integer ejercicioId, @RequestParam("rutinaId") Integer rutinaId, @RequestParam("sesionId") Integer sesionId, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        SesionejercicioDTO ejercicio = sesionEjercicioService.buscarPorId(ejercicioId);
        model.addAttribute("ejercicio", ejercicio);
        UsuarioDTO cliente = (UsuarioDTO) session.getAttribute("usuario");
        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorId(rutinaId);
        SesionentrenamientoDTO sesionentrenamiento = sesionEntrenamientoService.buscarPorId(sesionId);
        List<SesionentrenamientoHasSesionejercicioDTO> ejercicios = sesionentrenamientoHasSesionejercicioService.buscarPorSesionentrenamientoOrdenadoPorPosicion(sesionentrenamiento.getId());
        List<SesionejercicioDTO> sesionesEjercicio = ejercicios.stream().map(SesionentrenamientoHasSesionejercicioDTO::getSesionejercicio).toList();
        List<ValoracionDTO>  valoraciones = valoracionService.buscarPorUsuarioYRutinaAsignadaYSesionejercicioDentro(cliente.getId(),rutinaAsignada.getId(), sesionesEjercicio);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("rutinaAsignada",rutinaAsignada);
        model.addAttribute("sesionEntrenamiento",sesionentrenamiento);
        return "ejercicioCliente"; // Retorna el nombre de la vista de cliente
    }

    @PostMapping("/clienteMain/rutina/sesion/ejercicio")
    @Transactional
    public String doEjercicoPost(@RequestParam("rutinaId") Integer rutinaId, @RequestParam("rating") Integer rating, @RequestParam("ejercicio") Integer ejercicioId,
                                 @RequestParam("comentario") String comentario,@RequestParam("sesionId") Integer sesionId ,Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        SesionejercicioDTO sesionejercicio = sesionEjercicioService.buscarPorId(ejercicioId);

        ValoracionDTO valoracion = new ValoracionDTO();

        valoracion.setDescripcion(comentario);
        valoracion.setPuntuacion(rating);
        LocalDate fecha = LocalDate.now();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
        Date fechafinal = Date.valueOf(lunes);
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");

        usuario = usuarioService.buscarUsuario(usuario.getId());

        RutinaAsignadaDTO rutinaAsignada = rutinaAsignadaService.buscarPorUsuarioYFecha(usuario, fechafinal);

        valoracion.setRutinaAsignada(rutinaAsignada);
        valoracion.setUsuario(usuario);
        valoracion.setSesionejercicio(sesionejercicio);

        this.valoracionRepository.save(valoracion);

        return "redirect:/clienteMain/rutina/sesion?rutinaId=" + rutinaId + "&id=" + sesionId; // Retorna el nombre de la vista de cliente
    }
}
