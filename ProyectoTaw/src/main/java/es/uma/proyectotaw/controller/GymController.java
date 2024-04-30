package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.RolEnum;
import es.uma.proyectotaw.entity.TrolEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
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

import java.util.Objects;
import java.util.Optional;

@Controller
public class GymController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrolRepository trolRepository;
    @GetMapping("/")
    public String redirectToInicio(Model model) {
        return "redirect:/inicio";
    }
    @GetMapping("/inicio")
    public String doInicio(Model model) {
        return "inicio";
    }
    @GetMapping("/trabaja")
    public String doTrabaja(Model model) {
        return "trabaja";
    }
    @GetMapping("/acceso")
    public String doAcceso(Model model) {
        return "acceso";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        Model model, HttpSession session) {
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuarioAndContraseña(username, password);
        if (usuario != null) {
            RolEnum rol = usuario.getRol().getRol();
            session.setAttribute("usuario", usuario);
            if(rol == RolEnum.admin) {
                return "redirect:/adminMain";
            }else if(rol == RolEnum.entrenador) {
                return "redirect:/entrenadorMain";
            }else if(rol == RolEnum.cliente){
                    return "redirect:/clienteMain";
            }
        }
        model.addAttribute("error", "Nombre de usuario o contraseña incorrectos");
        return "acceso"; // Reenvía al mismo formulario de inicio de sesión
    }


    @GetMapping("/registro")
    public String doRegistro(Model model) {
        return "registro";
    }
    @GetMapping("/adminMain")
    public String doAdminMain() {
        // Aquí puedes agregar lógica para obtener datos necesarios para la vista de administrador
        return "mainAdmin"; // Retorna el nombre de la vista de administrador
    }



    @GetMapping("/clienteMain")
    public String doClienteMain() {
        // Aquí puedes agregar lógica para obtener datos necesarios para la vista de cliente
        return "mainCliente"; // Retorna el nombre de la vista de cliente
    }


}
