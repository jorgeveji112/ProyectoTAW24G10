package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SesionEntrenadorController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;


    @GetMapping("/entrenadorMain/sesiones")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<SesionentrenamientoEntity> sesiones = sesionentrenamientoRepository.findByUsuarioId(usuario.getId());
        model.addAttribute("sesiones", sesiones);
        return "sesionesEntrenador";
    }

    @GetMapping("/entrenadorMain/sesiones/ver")
    public String doVerSesion(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        List<SesionentrenamientoHasSesionejercicioEntity> listaSesionHasSesion = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesion);

        model.addAttribute("listaSesionHasSesion", listaSesionHasSesion);
        model.addAttribute("sesion", sesion);

        return "verSesionEntrenador";
    }
}
