package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.RutinaPredefinidaRepository;
import es.uma.proyectotaw.repository.SesionentrenamientoRepository;
import es.uma.proyectotaw.repository.TrolRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SesionEntrenadorController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;


    @GetMapping("/entrenadorMain/sesiones")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<SesionentrenamientoEntity> sesiones = sesionentrenamientoRepository.findByUsuarioId(usuario.getId());
        model.addAttribute("sesiones", sesiones);
        return "sesionesEntrenador";
    }
}
