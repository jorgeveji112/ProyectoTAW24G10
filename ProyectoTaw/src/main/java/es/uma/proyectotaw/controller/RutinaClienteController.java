package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
public class RutinaClienteController extends BaseController{

    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;

    @Autowired
    private RutinaSesionentrenamientoRepository rutinasesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;

    @Autowired
    private SesionejercicioRepository sesionejercicioRepository;




    @GetMapping("/clienteMain/rutina")
    public String doRutina(@RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findByUsuarioAndFecha(cliente.getUsuario(),fechaDate).orElse(null);
        List<RutinaSesionentrenamientoEntity> rutinaSesionentrenamiento = this.rutinasesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaAsignada.getRutinaPredefinida());

        model.addAttribute("sesiones", rutinaSesionentrenamiento);
        model.addAttribute("rutinaAsignada", rutinaAsignada);
        model.addAttribute("cliente", cliente);
        return "rutinaCliente"; // Retorna el nombre de la vista de cliente
    }

    @GetMapping("/clienteMain/rutina/sesion")
    public String doSesion(@RequestParam("id") Integer sesionId, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoEntity sesionentrenamiento = this.sesionentrenamientoRepository.findById(sesionId).orElse(null);
        List<SesionentrenamientoHasSesionejercicioEntity> ejercicios = this.sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesionentrenamiento);

        model.addAttribute("ejercicios", ejercicios);
        session.setAttribute("sesion", sesionentrenamiento);
        model.addAttribute("sesion", sesionentrenamiento);

        return "sesionCliente"; // Retorna el nombre de la vista de cliente
    }

    @GetMapping("/clienteMain/rutina/sesion/ejercicio")
    public String doEjercico(@RequestParam("id") Integer ejercicioId, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionejercicioEntity ejercicio = this.sesionejercicioRepository.findById(ejercicioId).orElse(null);
        model.addAttribute("ejercicio", ejercicio);

        return "ejercicioCliente"; // Retorna el nombre de la vista de cliente
    }

    @PostMapping("/clienteMain/rutina/sesion/ejercicio")
    public String doEjercicoPost(@RequestParam("rating") Integer rating, @RequestParam("ejercicio") Integer ejercicioId,
                                 @RequestParam("comentario") String comentario, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionejercicioEntity sesionejercicio = this.sesionejercicioRepository.findById(ejercicioId).orElse(null);
        SesionentrenamientoEntity sesionentrenamiento = (SesionentrenamientoEntity )session.getAttribute("sesion");

        ValoracionEntity valoracion = new ValoracionEntity();

        valoracion.setDescripcion(comentario);
        valoracion.setPuntuacion(rating);
        LocalDate fecha = LocalDate.now();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
        Date fechafinal = Date.valueOf(lunes);
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findByUsuarioAndFecha(usuario,fechafinal).orElse(null);
        valoracion.setRutinaAsignadaId(rutinaAsignada.getId());
        valoracion.setUsuarioId(usuario.getId());
        valoracion.setSesionejercicioId(sesionejercicio.getId());

        //this

        return "redirect:/clienteMain/rutina/sesion?id=" + sesionentrenamiento.getId() ; // Retorna el nombre de la vista de cliente
    }
}
