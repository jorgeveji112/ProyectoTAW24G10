/*
        Creador: Jorge Velázquez Jiménez
*/
package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class DesarrolloClienteController extends BaseController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;

    @Autowired
    private RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;

    @Autowired
    private ValoracionRepository valoracionRepository;

    @GetMapping("/clienteMain/desarrollo")
    public String doDesarrollo(@RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        UsuarioEntity usuario = usuarioRepository.findById(cliente.getId()).orElse(null);

        model.addAttribute("usuario", usuario);
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);
        Optional<RutinaAsignadaEntity> rutinaAsignada = Optional.ofNullable(rutinaAsignadaRepository.findByUsuarioAndFecha(usuario, fechaDate));

        if (rutinaAsignada.isPresent()) {
            model.addAttribute("rutinaAsignada", rutinaAsignada.get());
            List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaAsignada.get().getRutinaPredefinida());
            model.addAttribute("rutinasSesiones", rutinasSesiones);
        } else {
            model.addAttribute("rutinaAsignada", null);
        }

        return "desarrolloCliente";
    }
    @GetMapping("/clienteMain/desarrollo/sesion")
    public String verSesion(@RequestParam("rutina") Integer rutinaId ,@RequestParam("sesion") Integer sesionId,Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        ClienteEntity cliente = (ClienteEntity) session.getAttribute("cliente");
        UsuarioEntity usuario = usuarioRepository.findById(cliente.getId()).orElse(null);
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(sesionId).orElse(null);
        RutinaAsignadaEntity rutinaAsignada = rutinaAsignadaRepository.findById(rutinaId).orElse(null);
        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesionesEjercicios = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        List<SesionejercicioEntity> sesionesEjercicio = sesionesHasSesionesEjercicios.stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
        List<ValoracionEntity>  valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(usuario,rutinaAsignada, sesionesEjercicio);
        LocalDate fechaLocal = rutinaAsignada.getFecha().toLocalDate();
        model.addAttribute("rutinaAsignada", rutinaAsignada);
        model.addAttribute("semana", fechaLocal);
        model.addAttribute("sesion", sesion);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("sesionesEjercicio", sesionesEjercicio);
        model.addAttribute("usuario", usuario);
        return "valoracionesCliente";

    }
}