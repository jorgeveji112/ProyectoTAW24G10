package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.RolEnum;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.TrolEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import es.uma.proyectotaw.repository.RutinaPredefinidaRepository;
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
public class RutinaEntrenadorController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;

    @GetMapping("/entrenadorMain")
    public String doEntrenadorMain(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "redirect:/entrenadorMain/inicio";
    }
    @GetMapping("/entrenadorMain/inicio")
    public String doMainInicio(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "mainEntrenador";
    }

    @GetMapping("/entrenadorMain/rutinas")
    public String doRutinas(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        List<RutinaPredefinidaEntity> rutinas = rutinaPredefinidaRepository.findByUsuarioId(usuario.getId());
        model.addAttribute("rutinas", rutinas);
        return "rutinasEntrenador";
    }
}
