/*
        Creador: Jorge Velázquez Jiménez
*/
package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.dao.*;
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
    private UsuarioRepository usuarioRepository;

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

    @Autowired
    private ValoracionRepository valoracionRepository;

    @GetMapping("/clienteMain/rutina")
    public String doRutina(@RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findByUsuarioAndFecha(cliente.getUsuario(), fechaDate);
        if(rutinaAsignada != null){
            List<RutinaSesionentrenamientoEntity> rutinaSesionentrenamiento = this.rutinasesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaAsignada.getRutinaPredefinida());
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
        SesionentrenamientoEntity sesionentrenamiento = this.sesionentrenamientoRepository.findById(sesionId).orElse(null);
        UsuarioEntity cliente = (UsuarioEntity) session.getAttribute("usuario");
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(rutinaId).orElse(null);
        List<SesionentrenamientoHasSesionejercicioEntity> ejercicios = this.sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesionentrenamiento);
        List<SesionejercicioEntity> sesionesEjercicio = ejercicios.stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
        List<ValoracionEntity>  valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(cliente,rutinaAsignada, sesionesEjercicio);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("sesion", sesionentrenamiento);
        model.addAttribute("rutinaAsignada", rutinaAsignada);

        return "sesionCliente"; // Retorna el nombre de la vista de cliente
    }

    @GetMapping("/clienteMain/rutina/sesion/ejercicio")
    public String doEjercico(@RequestParam("id") Integer ejercicioId, @RequestParam("rutinaId") Integer rutinaId, @RequestParam("sesionId") Integer sesionId, Model model, HttpSession session) {
        if (!estaAutenticado(session)) return "redirect:/acceso";
        SesionejercicioEntity ejercicio = this.sesionejercicioRepository.findById(ejercicioId).orElse(null);
        model.addAttribute("ejercicio", ejercicio);
        UsuarioEntity cliente = (UsuarioEntity) session.getAttribute("usuario");
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(rutinaId).orElse(null);
        SesionentrenamientoEntity sesionentrenamiento = this.sesionentrenamientoRepository.findById(sesionId).orElse(null);
        List<SesionentrenamientoHasSesionejercicioEntity> ejercicios = this.sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesionentrenamiento);
        List<SesionejercicioEntity> sesionesEjercicio = ejercicios.stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
        List<ValoracionEntity>  valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(cliente,rutinaAsignada, sesionesEjercicio);
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
        SesionejercicioEntity sesionejercicio = this.sesionejercicioRepository.findById(ejercicioId).orElse(null);

        ValoracionEntity valoracion = new ValoracionEntity();

        valoracion.setDescripcion(comentario);
        valoracion.setPuntuacion(rating);
        LocalDate fecha = LocalDate.now();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY);
        Date fechafinal = Date.valueOf(lunes);
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");

        // Reload the usuario entity from the repository to ensure it is managed
        usuario = this.usuarioRepository.findById(usuario.getId()).orElse(null);

        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findByUsuarioAndFecha(usuario, fechafinal);

        valoracion.setRutinaAsignada(rutinaAsignada);
        valoracion.setUsuario(usuario);
        valoracion.setSesionejercicio(sesionejercicio);

        this.valoracionRepository.save(valoracion);

        return "redirect:/clienteMain/rutina/sesion?rutinaId=" + rutinaId + "&id=" + sesionId; // Retorna el nombre de la vista de cliente
    }
}
