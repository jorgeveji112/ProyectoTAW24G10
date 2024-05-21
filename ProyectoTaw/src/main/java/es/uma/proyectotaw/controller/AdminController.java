package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.RolEnum;
import es.uma.proyectotaw.entity.UsuarioEntity;
import es.uma.proyectotaw.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

// Carlos Gálvez Bravo
@Controller
public class AdminController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/adminMain/inicio")
    public String doAdminMain() {
        return "mainAdmin";
    }

    @GetMapping("adminMain/entrenadores")
    public String doEntrenadores(HttpSession session, Model model) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        List<UsuarioEntity> listaUsuarios = usuarioRepository.findAll();
        List<UsuarioEntity> entrenadoresBodyBuilding = new ArrayList<>();
        List<UsuarioEntity> entrenadoresCrossTraining = new ArrayList<>();
        for (UsuarioEntity usuario : listaUsuarios) {
            if(usuario.getRol().getRol() == RolEnum.entrenador){
                if(usuario.getTipoEntrenamiento().getId() == 1) {
                    entrenadoresBodyBuilding.add(usuario);
                } else {
                    entrenadoresCrossTraining.add(usuario);
                }
            }
        }
        model.addAttribute("entrenadoresBodyBuilding", entrenadoresBodyBuilding);
        model.addAttribute("entrenadoresCrossTraining", entrenadoresCrossTraining);
        return "listaEntrenadores";
    }

    @GetMapping("adminMain/clientes")
    public String doClientes(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("adminMain/solicitudes")
    public String doSolicitudes(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }

    @GetMapping("adminMain/ejercicios")
    public String doEjercicios(HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";
        return "mainAdmin";
    }
}
