package es.uma.proyectotaw.controller;

import es.uma.proyectotaw.entity.*;
import es.uma.proyectotaw.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import es.uma.proyectotaw.repository.UsuarioRepository;

@Controller
public class RutinaClienteController extends BaseController{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clienteMain/rutina")
    public String doRutina(Model model, HttpSession session) {
        if(!estaAutenticado(session)) return "redirect:/acceso";

        return "rutinaCliente"; // Retorna el nombre de la vista de cliente
    }


}
