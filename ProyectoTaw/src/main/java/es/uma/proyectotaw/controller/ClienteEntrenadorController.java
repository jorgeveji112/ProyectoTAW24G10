package es.uma.proyectotaw.controller;


import es.uma.proyectotaw.entity.*;

import es.uma.proyectotaw.dao.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteEntrenadorController extends  BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;

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
    @GetMapping("/entrenadorMain/clientes")
    public String doClientes(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<UsuarioEntity> clientes = usuarioRepository.findClientesByEntrenadorId(usuario.getId());
        model.addAttribute("clientes", clientes);
        return "clientesEntrenador";
    }

    @GetMapping("/entrenadorMain/clientes/entrenamiento")
    public String doClientesEntrenamiento(@RequestParam("id") Integer clienteId, @RequestParam("fecha") String fecha, Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        UsuarioEntity cliente = usuarioRepository.findById(clienteId).get();
        List<RutinaPredefinidaEntity> rutinasEntrenador = rutinaPredefinidaRepository.findByUsuario(usuario);
        model.addAttribute("rutinasEntrenador", rutinasEntrenador);
        model.addAttribute("cliente", cliente);
        Date fechaDate = Date.valueOf(fecha);
        LocalDate fechaLocal = fechaDate.toLocalDate();
        model.addAttribute("semana", fechaLocal);
        Optional<RutinaAsignadaEntity> rutinaAsignada = rutinaAsignadaRepository.findByUsuarioAndFecha(cliente, fechaDate);
        if (rutinaAsignada.isPresent()) {
            model.addAttribute("rutinaAsignada", rutinaAsignada.get());
            List<RutinaSesionentrenamientoEntity> rutinasSesiones = rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaAsignada.get().getRutinaPredefinida());
            model.addAttribute("rutinasSesiones", rutinasSesiones);
        } else {
            model.addAttribute("rutinaAsignada", null);
        }
        return "asignarRutinaEntrenador";
    }
    @PostMapping("/entrenadorMain/clientes/entrenamiento/asignarRutina")
    public String crearRutinaAsignada(@RequestParam("rutinaId") Integer rutinaId, @RequestParam("usuarioId") Integer usuarioId,@RequestParam("fecha") LocalDate fecha, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        RutinaAsignadaEntity rutinaAsignada = new RutinaAsignadaEntity();
        rutinaAsignada.setRutinaPredefinida(rutinaPredefinidaRepository.findById(rutinaId).get());
        rutinaAsignada.setUsuario(usuarioRepository.findById(usuarioId).get());
        rutinaAsignada.setFecha(Date.valueOf(fecha));
        rutinaAsignadaRepository.save(rutinaAsignada);
        return "redirect:/entrenadorMain/clientes/entrenamiento?id="+usuarioId+"&fecha="+fecha.toString(); // Redirige a la página deseada después de asignar la rutina
    }

    @GetMapping("/entrenadorMain/clientes/entrenamiento/sesion")
    public String verValoracionSesion(@RequestParam("id") Integer clienteId, @RequestParam("rutina") Integer rutinaId ,@RequestParam("sesion") Integer sesionId,Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        UsuarioEntity cliente = usuarioRepository.findById(clienteId).get();
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(sesionId).get();
        RutinaAsignadaEntity rutinaAsignada = rutinaAsignadaRepository.findById(rutinaId).get();
        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesionesEjercicios = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);
        List<SesionejercicioEntity> sesionesEjercicio = sesionesHasSesionesEjercicios.stream().map(SesionentrenamientoHasSesionejercicioEntity::getSesionejercicio).toList();
        List<ValoracionEntity>  valoraciones = valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(cliente,rutinaAsignada, sesionesEjercicio);
        LocalDate fechaLocal = rutinaAsignada.getFecha().toLocalDate();
        model.addAttribute("rutinaAsignada", rutinaAsignada);
        model.addAttribute("semana", fechaLocal);
        model.addAttribute("sesion", sesion);
        model.addAttribute("valoraciones", valoraciones);
        model.addAttribute("sesionesEjercicio", sesionesEjercicio);
        model.addAttribute("cliente", cliente);
        return "valoracionesSesionEntrenador";

    }

}
